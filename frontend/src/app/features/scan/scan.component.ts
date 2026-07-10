import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ScanService } from './scan.service';
import { Business, ScanRequest, ScanResponse } from './scan.models';

const DEVICE_TOKEN_KEY = 'nfctag.deviceToken';

/** Position GPS relevée par le navigateur (null si refusée). */
interface GeoPosition {
  latitude: number | null;
  longitude: number | null;
  accuracy: number | null;
}

@Component({
  selector: 'app-scan',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './scan.component.html',
  styleUrl: './scan.component.scss'
})
export class ScanComponent implements OnInit {

  scanToken = '';
  loading = true;
  error: string | null = null;
  result: ScanResponse | null = null;

  // 1er passage : formulaire d'identification
  needForm = false;
  businesses: Business[] = [];
  firstname = '';
  lastname = '';
  mobile = '';
  businessId: string | null = null;
  submitting = false;

  private position: GeoPosition = { latitude: null, longitude: null, accuracy: null };

  constructor(private route: ActivatedRoute, private api: ScanService) {}

  ngOnInit(): void {
    this.scanToken = this.route.snapshot.paramMap.get('token') ?? '';

    // On relève d'abord la position (obligatoire pour vérifier la proximité du tag)
    this.getPosition().then((pos) => {
      this.position = pos;
      const deviceToken = localStorage.getItem(DEVICE_TOKEN_KEY);

      if (deviceToken) {
        // Technicien déjà connu sur ce navigateur → scan direct
        this.sendScan(deviceToken);
      } else {
        // 1er passage → formulaire (nom, prénom, mobile, société)
        this.showForm();
      }
    });
  }

  /** Envoie le scan au back (arrivée ou départ, décidé côté serveur). */
  private sendScan(deviceToken: string | null): void {
    const request: ScanRequest = {
      deviceToken: deviceToken,
      latitude: this.position.latitude,
      longitude: this.position.longitude,
      accuracy: this.position.accuracy,
      firstname: deviceToken ? null : this.firstname.trim(),
      lastname: deviceToken ? null : this.lastname.trim(),
      mobile: deviceToken ? null : this.mobile.trim(),
      businessId: deviceToken ? null : this.businessId
    };

    this.api.scan(this.scanToken, request).subscribe({
      next: (r) => this.handle(r),
      error: (e) => {
        // Jeton inconnu (base réinitialisée ?) → on oublie le jeton et on repasse par le formulaire
        if (deviceToken && e?.status === 404) {
          localStorage.removeItem(DEVICE_TOKEN_KEY);
          this.showForm();
        } else {
          this.fail(e);
        }
      }
    });
  }

  /** Affiche le formulaire du 1er passage (avec la liste des sociétés). */
  private showForm(): void {
    this.api.businesses().subscribe({
      next: (list) => { this.businesses = list; this.needForm = true; this.loading = false; },
      error: (e) => this.fail(e)
    });
  }

  /** Validation du formulaire du 1er passage. */
  submitForm(): void {
    if (!this.canSubmit) { return; }
    this.submitting = true;
    this.sendScan(null);
  }

  get canSubmit(): boolean {
    return !!(this.firstname.trim() && this.lastname.trim() && this.mobile.trim() && this.businessId);
  }

  private handle(r: ScanResponse): void {
    this.loading = false;
    this.submitting = false;
    this.needForm = false;
    this.result = r;
    // On mémorise le jeton pour être reconnu aux prochains passages
    if (r.deviceToken) {
      localStorage.setItem(DEVICE_TOKEN_KEY, r.deviceToken);
    }
  }

  private fail(e: any): void {
    this.loading = false;
    this.submitting = false;
    const body = e?.error;
    this.error = typeof body === 'string' && body
      ? body
      : body?.message ?? 'Tag inconnu ou erreur réseau.';
  }

  get isArrival(): boolean {
    return this.result?.action === 'ARRIVAL';
  }

  /** Demande la position au navigateur ; renvoie des null si refusée/indisponible. */
  private getPosition(): Promise<GeoPosition> {
    return new Promise((resolve) => {
      if (!navigator.geolocation) {
        resolve({ latitude: null, longitude: null, accuracy: null });
        return;
      }
      navigator.geolocation.getCurrentPosition(
        (pos) => resolve({
          latitude: pos.coords.latitude,
          longitude: pos.coords.longitude,
          accuracy: pos.coords.accuracy
        }),
        () => resolve({ latitude: null, longitude: null, accuracy: null }),
        { enableHighAccuracy: true, timeout: 8000, maximumAge: 0 }
      );
    });
  }
}
