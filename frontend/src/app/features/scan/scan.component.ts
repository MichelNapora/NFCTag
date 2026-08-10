import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ScanService } from './scan.service';
import { Business, ScanRequest, ScanResponse } from './scan.models';
import {AuthService} from '../../common/auth/auth.service';
import {CurrentEmployee} from '../../common/auth/auth.models';
import { nameError } from '../../common/utils/name-error';
import { mobileError } from '../../common/utils/mobile-error';
import { mobileDigits } from '../../common/utils/mobile-digits';
import {errorMessage} from '../../common/utils/http-error';
import {
  CALIBRATION_FAILED,
  CALIBRATION_TOO_IMPRECISE, POSITION_UNAVAILABLE,
  SCAN_FAILED,
  SCAN_IDENTITY_MISMATCH,
  SCAN_TAG_UNKNOWN
} from '../../common/messages';

const DEVICE_TOKEN_KEY = 'nfctag.deviceToken';

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
  needForm = false;
  businesses: Business[] = [];
  firstname = '';
  lastname = '';
  mobile = '';
  businessId: string | null = null;
  submitting = false;

  employee: CurrentEmployee | null = null;
  calibrating = false;
  calibratedAt: string | null = null;
  calibrationError: string | null = null;

  private position: GeoPosition = { latitude: null, longitude: null, accuracy: null };

  get accuracy(): number | null {
    return this.position.accuracy;
  }

  constructor(
    private route: ActivatedRoute,
    private api: ScanService,
    private auth: AuthService
  ) {}

  ngOnInit(): void {
    this.scanToken = this.route.snapshot.paramMap.get('token') ?? '';

    this.getPosition().then((pos) => {
      this.position = pos;

      this.auth.isLoggedIn().subscribe((loggedIn) => {
        if (loggedIn) {
          this.employee = this.auth.employee;
          this.loading = false;
          return;
        }

        this.sendScan(localStorage.getItem(DEVICE_TOKEN_KEY), true);
      });
    });
  }

  private sendScan(deviceToken: string | null, auto = false): void {
    const request: ScanRequest = {
      deviceToken: deviceToken,
      latitude: this.position.latitude,
      longitude: this.position.longitude,
      accuracy: this.position.accuracy,
      firstname: deviceToken ? null : this.ouNull(this.firstname),
      lastname: deviceToken ? null : this.ouNull(this.lastname),
      mobile: deviceToken ? null : this.ouNull(mobileDigits(this.mobile)),
      businessId: deviceToken ? null : this.businessId
    };

    this.api.scan(this.scanToken, request).subscribe({
      next: (r) => this.handle(r),
      error: (e) => {
        if (auto && (e?.status === 400 || e?.status === 404)) {
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

  /**
   * Une chaîne vide n'est pas une valeur : le back doit recevoir null, sinon @Size la refuse.
   * C'est le cas quand le localStorage a été purgé : le cookie prend le relais, le formulaire est vide.
   */
  private ouNull(value: string): string | null {
    return value.trim() || null;
  }

  /** Messages de format, affichés sous le champ. null = rien à signaler. */
  get firstnameMessage(): string | null { return nameError(this.firstname); }

  get lastnameMessage(): string | null { return nameError(this.lastname); }

  get mobileMessage(): string | null { return mobileError(this.mobile); }

  get canSubmit(): boolean {
    return !!(this.firstname.trim() && this.lastname.trim() && this.mobile.trim() && this.businessId)
      && !this.firstnameMessage && !this.lastnameMessage && !this.mobileMessage;
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
    if (e?.status === 404) { this.error = SCAN_TAG_UNKNOWN; return; }
    if (e?.status === 401) { this.error = SCAN_IDENTITY_MISMATCH; return; }
    this.error = errorMessage(e, SCAN_FAILED);
  }

  get isArrival(): boolean {
    return this.result?.action === 'ARRIVAL';
  }

  /** Enregistre la position actuelle comme position du tag (employé connecté). */
  calibrate(): void {
    if (this.position.latitude == null || this.position.longitude == null
      || this.position.accuracy == null) {
      this.calibrationError = POSITION_UNAVAILABLE;
      return;
    }

    this.calibrating = true;
    this.calibrationError = null;

    this.api.calibrate(this.scanToken, {
      latitude: this.position.latitude,
      longitude: this.position.longitude,
      accuracy: this.position.accuracy
    }).subscribe({
      next: (tag) => {
        this.calibrating = false;
        this.calibratedAt = tag.calibratedAt;
      },
      error: (e) => {
        this.calibrating = false;
        this.calibrationError = e?.status === 400
          ? CALIBRATION_TOO_IMPRECISE
          : errorMessage(e, CALIBRATION_FAILED);
      }
    });
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
