import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ScanService } from './scan.service';
import { ScanResult } from './scan.models';

const DEVICE_TOKEN_KEY = 'nfctag.deviceToken';

@Component({
  selector: 'app-scan',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './scan.component.html',
  styleUrl: './scan.component.scss'
})
export class ScanComponent implements OnInit {

  tagToken = '';
  loading = true;
  error: string | null = null;
  result: ScanResult | null = null;

  // saisies des replis d'identification
  mobile = '';
  businessId: number | null = null;
  firstname = '';
  lastname = '';
  submitting = false;

  constructor(private route: ActivatedRoute, private api: ScanService) {}

  ngOnInit(): void {
    this.tagToken = this.route.snapshot.paramMap.get('token') ?? '';
    const deviceToken = localStorage.getItem(DEVICE_TOKEN_KEY);
    this.api.scan(this.tagToken, deviceToken).subscribe({
      next: (r) => this.handle(r),
      error: (e) => this.fail(e)
    });
  }

  /** Repli n°1 : le technicien saisit son mobile. */
  submitMobile(): void {
    if (!this.mobile.trim()) { return; }
    this.submitting = true;
    this.api.lookup(this.tagToken, this.mobile.trim()).subscribe({
      next: (r) => { this.submitting = false; this.handle(r); },
      error: (e) => { this.submitting = false; this.fail(e); }
    });
  }

  /** Repli n°2 : premier passage, choix de la société. */
  submitBusiness(): void {
    if (!this.businessId) { return; }
    this.submitting = true;
    this.api.register(this.tagToken, this.mobile.trim(), this.businessId,
                      this.firstname.trim(), this.lastname.trim()).subscribe({
      next: (r) => { this.submitting = false; this.handle(r); },
      error: (e) => { this.submitting = false; this.fail(e); }
    });
  }

  private handle(r: ScanResult): void {
    this.loading = false;
    this.result = r;
    // On mémorise le jeton appareil pour les prochains passages.
    if (r.deviceToken) {
      localStorage.setItem(DEVICE_TOKEN_KEY, r.deviceToken);
    }
  }

  private fail(e: any): void {
    this.loading = false;
    this.submitting = false;
    this.error = e?.error?.message ?? 'Tag inconnu ou erreur réseau.';
  }

  get isArrival(): boolean {
    return this.result?.action === 'ARRIVAL';
  }
}
