import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BusinessService } from './business.service';
import { Business } from './business.models';
import { CountsService } from '../../common/shell/counts.service';
import { AuthService } from '../../common/auth/auth.service';
import { errorMessage } from '../../common/utils/http-error';
import { ConfirmService } from '../../common/confirm/confirm.service';

interface BusinessForm {
  id: string | null;
  name: string;
  bce: string;
}

@Component({
  selector: 'app-businesses',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './businesses.component.html',
  styleUrl: './businesses.component.scss'
})
export class BusinessesComponent implements OnInit {

  businesses: Business[] = [];
  loading = true;
  idsInUse: string[] = [];
  error: string | null = null;
  private errorTimer: ReturnType<typeof setTimeout> | null = null;
  query = '';

  modalOpen = false;
  form: BusinessForm = { id: null, name: '', bce: '' };
  saving = false;

  constructor(
    private businessService: BusinessService,
    private counts: CountsService,
    public auth: AuthService,
    private confirm: ConfirmService
  ) {}

  ngOnInit(): void {
    this.reload();
  }

  reload(): void {
    this.loading = true;
    this.businessService.idsInUse().subscribe({ next: (d) => this.idsInUse = d });

    this.businessService.findAll().subscribe({
      next: (d) => { this.businesses = d; this.loading = false; },
      error: (e) => this.fail(e)
    });
  }

  get filtered(): Business[] {
    const q = this.query.trim().toLowerCase();
    return this.businesses.filter(b =>
      !q || b.name.toLowerCase().includes(q) || b.bce.includes(q)
    );
  }

  openCreate(): void {
    this.form = { id: null, name: '', bce: '' };
    this.modalOpen = true;
  }

  openEdit(b: Business): void {
    this.form = { id: b.id, name: b.name, bce: b.bce };
    this.modalOpen = true;
  }

  close(): void {
    this.modalOpen = false;
  }

  get canSave(): boolean {
    return !!(this.form.name.trim() && this.form.bce.trim());
  }

  save(): void {
    if (!this.canSave || this.saving) { return; }
    this.saving = true;
    const f = this.form;
    const call = f.id
      ? this.businessService.update(f.id, f.name.trim(), f.bce.trim())
      : this.businessService.create(f.name.trim(), f.bce.trim());

    call.subscribe({
      next: () => { this.saving = false; this.modalOpen = false; this.reload(); this.counts.refresh(); },
      error: (e) => { this.saving = false; this.fail(e); }
    });
  }

  canDelete(id: string): boolean {
    return !this.idsInUse.includes(id);
  }

  remove(b: Business): void {
    this.confirm.ask({
      title: 'Supprimer la société',
      message: `Voulez-vous supprimer « ${b.name} » ?`,
      confirmLabel: 'Supprimer',
      danger: true,
      action: () => this.businessService.delete(b.id)
    }).subscribe(() => { this.reload(); this.counts.refresh(); });
  }

  private fail(e: any): void {
    this.loading = false;
    this.error = errorMessage(e, 'Une erreur est survenue.');
    if (this.errorTimer) { clearTimeout(this.errorTimer); }
    this.errorTimer = setTimeout(() => this.error = null, 5000);
  }
}
