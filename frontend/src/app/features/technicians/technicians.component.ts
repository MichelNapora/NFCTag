import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TechnicianService } from './technician.service';
import { Technician } from './technician.models';
import { BusinessService } from '../businesses/business.service';
import { Business } from '../businesses/business.models';
import { AuthService } from '../../common/auth/auth.service';
import { errorMessage } from '../../common/utils/http-error';
import {ConfirmService} from '../../common/confirm/confirm.service';
import { CountsService } from '../../common/shell/counts.service';
import {
  CONFIRM_DELETE_TECHNICIAN,
  GENERIC_ERROR,
  BUTTON_DELETE,
  TITLE_DELETE_TECHNICIAN,
  ALREADY_EXISTS
} from '../../common/messages';
import { format } from '../../common/utils/format';
import { nameError } from '../../common/utils/name-error';
import { mobileError } from '../../common/utils/mobile-error';
import { mobileDigits } from '../../common/utils/mobile-digits';

interface TechnicianForm {
  id: string | null;
  firstname: string;
  lastname: string;
  mobile: string;
  businessId: string | null;
}

@Component({
  selector: 'app-technicians',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './technicians.component.html',
  styleUrl: './technicians.component.scss'
})
export class TechniciansComponent implements OnInit {

  technicians: Technician[] = [];
  businesses: Business[] = [];
  loading = true;
  idsInUse: string[] = [];
  error: string | null = null;
  private errorTimer: ReturnType<typeof setTimeout> | null = null;
  query = '';

  modalOpen = false;
  form: TechnicianForm = { id: null, firstname: '', lastname: '', mobile: '', businessId: null };
  saving = false;

  constructor(
    private technicianService: TechnicianService,
    private businessService: BusinessService,
    public auth: AuthService,
    private confirm: ConfirmService,
    private counts: CountsService
  ) {}

  ngOnInit(): void {
    this.reload();
    this.businessService.findAll().subscribe({
      next: (d) => this.businesses = d,
      error: (e) => this.fail(e)
    });
  }

  reload(): void {
    this.loading = true;
    this.technicianService.idsInUse().subscribe({ next: (d) => this.idsInUse = d });

    this.technicianService.findAll().subscribe({
      next: (d) => { this.technicians = d; this.loading = false; },
      error: (e) => this.fail(e)
    });
  }

  businessName(businessId: string): string {
    return this.businesses.find(b => b.id === businessId)?.name ?? '—';
  }

  get filtered(): Technician[] {
    const q = this.query.trim().toLowerCase();
    return this.technicians.filter(t =>
      !q ||
      t.firstname.toLowerCase().includes(q) ||
      t.lastname.toLowerCase().includes(q) ||
      t.mobile.toLowerCase().includes(q) ||
      this.businessName(t.businessId).toLowerCase().includes(q)
    );
  }

  openEdit(t: Technician): void {
    this.form = {
      id: t.id,
      firstname: t.firstname,
      lastname: t.lastname,
      mobile: t.mobile,
      businessId: t.businessId
    };
    this.modalOpen = true;
  }

  close(): void {
    this.modalOpen = false;
  }


  get firstnameMessage(): string | null { return nameError(this.form.firstname); }

  get lastnameMessage(): string | null { return nameError(this.form.lastname); }

  get mobileMessage(): string | null { return mobileError(this.form.mobile); }

  get canSave(): boolean {
    return !!(this.form.firstname.trim() && this.form.lastname.trim()
        && this.form.mobile.trim() && this.form.businessId)
      && !this.firstnameMessage && !this.lastnameMessage && !this.mobileMessage;
  }

  save(): void {
    const f = this.form;
    if (!this.canSave || this.saving || !f.id) { return; }
    this.saving = true;

    this.technicianService.update(f.id, f.firstname.trim(), f.lastname.trim(), mobileDigits(f.mobile), f.businessId!)
      .subscribe({
        next: () => { this.saving = false; this.modalOpen = false; this.reload(); },
        error: (e) => { this.saving = false; this.fail(e); }
      });
  }

  canDelete(id: string): boolean {
    return !this.idsInUse.includes(id);
  }

  remove(t: Technician): void {
    this.confirm.ask({
      title: TITLE_DELETE_TECHNICIAN,
      message: format(CONFIRM_DELETE_TECHNICIAN, `${t.firstname} ${t.lastname}`),
      confirmLabel: BUTTON_DELETE,
      danger: true,
      action: () => this.technicianService.delete(t.id)
    }).subscribe(() => { this.reload(); this.counts.refresh(); });
  }

  private fail(e: any): void {
    this.loading = false;
    this.error = errorMessage(e, GENERIC_ERROR,ALREADY_EXISTS)
    if (this.errorTimer) { clearTimeout(this.errorTimer); }
    this.errorTimer = setTimeout(() => this.error = null, 5000);
  }
}
