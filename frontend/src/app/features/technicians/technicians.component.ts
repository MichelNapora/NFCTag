import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TechnicianService } from './technician.service';
import { Technician } from './technician.models';
import { BusinessService } from '../businesses/business.service';
import { Business } from '../businesses/business.models';
import { AuthService } from '../../common/auth/auth.service';
import { errorMessage } from '../../common/utils/http-error';

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
  error: string | null = null;
  private errorTimer: ReturnType<typeof setTimeout> | null = null;
  query = '';

  modalOpen = false;
  form: TechnicianForm = { id: null, firstname: '', lastname: '', mobile: '', businessId: null };
  saving = false;

  constructor(
    private technicianService: TechnicianService,
    private businessService: BusinessService,
    public auth: AuthService
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
    this.technicianService.findAll().subscribe({
      next: (d) => { this.technicians = d; this.loading = false; },
      error: (e) => this.fail(e)
    });
  }

  /** Nom de la société d'un technicien, pour l'affichage. */
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

  get canSave(): boolean {
    return !!(this.form.firstname.trim() && this.form.lastname.trim()
      && this.form.mobile.trim() && this.form.businessId);
  }

  save(): void {
    if (!this.canSave || this.saving || !this.form.id) { return; }
    this.saving = true;
    const f = this.form;

    this.technicianService.update(f.id, f.firstname.trim(), f.lastname.trim(), f.mobile.trim(), f.businessId!)
      .subscribe({
        next: () => { this.saving = false; this.modalOpen = false; this.reload(); },
        error: (e) => { this.saving = false; this.fail(e); }
      });
  }

  remove(t: Technician): void {
    if (!confirm(`Supprimer le technicien « ${t.firstname} ${t.lastname} » ?\nSes interventions seront perdues.`)) { return; }
    this.technicianService.delete(t.id).subscribe({
      next: () => this.reload(),
      error: (e) => this.fail(e)
    });
  }

  private fail(e: any): void {
    this.loading = false;
    this.error = errorMessage(e, 'Une erreur est survenue.');
    if (this.errorTimer) { clearTimeout(this.errorTimer); }
    this.errorTimer = setTimeout(() => this.error = null, 5000);
  }
}
