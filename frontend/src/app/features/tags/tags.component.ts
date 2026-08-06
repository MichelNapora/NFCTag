import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TagService } from './tag.service';
import { Tag } from './tag.models';
import { WingService } from '../wings/wing.service';
import { Wing } from '../wings/wing.models';
import { BuildingService } from '../buildings/building.service';
import { Building } from '../buildings/building.models';
import { CountsService } from '../../common/shell/counts.service';
import { AuthService } from '../../common/auth/auth.service';
import { errorMessage } from '../../common/utils/http-error';
import * as QRCode from 'qrcode';
import {ConfirmService} from '../../common/confirm/confirm.service';

interface TagForm {
  id: string | null;
  wingId: string | null;
  latitude: number | null;
  longitude: number | null;
}

@Component({
  selector: 'app-tags',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './tags.component.html',
  styleUrl: './tags.component.scss'
})
export class TagsComponent implements OnInit {

  tags: Tag[] = [];
  wings: Wing[] = [];
  buildings: Building[] = [];
  loading = true;
  idsInUse: string[] = [];
  error: string | null = null;
  private errorTimer: ReturnType<typeof setTimeout> | null = null;
  query = '';
  copied: string | null = null;

  modalOpen = false;
  form: TagForm = { id: null, wingId: null, latitude: null, longitude: null };
  saving = false;

  /** QR codes générés localement : id du tag → image (data URL). */
  qrCodes: Record<string, string> = {};
  /** Tag affiché en grand dans la modale QR (null = fermée). */
  qrTag: Tag | null = null;

  constructor(
    private tagService: TagService,
    private wingService: WingService,
    private buildingService: BuildingService,
    private counts: CountsService,
    public auth: AuthService,
    private confirm: ConfirmService
  ) {}

  ngOnInit(): void {
    this.reload();
  }

  reload(): void {
    this.loading = true;
    this.tagService.idsInUse().subscribe({ next: (d) => this.idsInUse = d });

    this.tagService.findAll().subscribe({
      next: (d) => { this.tags = d; this.loading = false; this.generateQrCodes(); },
      error: (e) => this.fail(e)
    });
    this.wingService.findAll().subscribe({ next: (d) => this.wings = d });
    this.buildingService.findAll().subscribe({ next: (d) => this.buildings = d });
  }

  /** Génère le QR de chaque tag dans le navigateur (aucun service externe). */
  private generateQrCodes(): void {
    for (const tag of this.tags) {
      QRCode.toDataURL(this.tagUrl(tag), { width: 480, margin: 1, color: { dark: '#111111', light: '#ffffff' } })
        .then(dataUrl => this.qrCodes[tag.id] = dataUrl);
    }
  }

  openQr(t: Tag): void {
    this.qrTag = t;
  }

  closeQr(): void {
    this.qrTag = null;
  }

  wingLabel(wingId: string): string {
    const wing = this.wings.find(w => w.id === wingId);
    if (!wing) { return '—'; }
    const building = this.buildings.find(b => b.id === wing.buildingId);
    return `${building?.name ?? '—'} / ${wing.name}`;
  }

  get availableWings(): Wing[] {
    return this.wings.filter(w =>
      !this.tags.some(t => t.wingId === w.id && t.id !== this.form.id)
    );
  }

  tagUrl(t: Tag): string {
    return `${location.origin}/scan/${t.scanToken}`;
  }

  get filtered(): Tag[] {
    const q = this.query.trim().toLowerCase();
    return this.tags.filter(t => !q || this.wingLabel(t.wingId).toLowerCase().includes(q));
  }

  openCreate(): void {
    this.form = { id: null, wingId: null, latitude: null, longitude: null };
    this.modalOpen = true;
  }

  openEdit(t: Tag): void {
    this.form = { id: t.id, wingId: t.wingId, latitude: t.latitude, longitude: t.longitude };
    this.modalOpen = true;
  }

  close(): void {
    this.modalOpen = false;
  }

  get canSave(): boolean {
    return !!this.form.wingId;
  }

  save(): void {
    if (!this.canSave || this.saving) { return; }
    this.saving = true;
    const f = this.form;
    const call = f.id
      ? this.tagService.update(f.id, f.wingId!, f.latitude, f.longitude)
      : this.tagService.create(f.wingId!, f.latitude, f.longitude);

    call.subscribe({
      next: () => { this.saving = false; this.modalOpen = false; this.reload(); this.counts.refresh(); },
      error: (e) => { this.saving = false; this.fail(e); }
    });
  }

  canDelete(id: string): boolean {
    return !this.idsInUse.includes(id);
  }


  remove(t: Tag): void {
    this.confirm.ask({
      title: 'Supprimer le tag',
      message: `Voulez-vous supprimer le tag « ${this.wingLabel(t.wingId)} » ?`,
      confirmLabel: 'Supprimer',
      danger: true,
      action: () => this.tagService.delete(t.id)
    }).subscribe(() => { this.reload(); this.counts.refresh(); });
  }

  recalibrate(t: Tag): void {
    this.confirm.ask({
      title: 'Recalibrer le tag',
      message: `La position du tag « ${this.wingLabel(t.wingId)} » sera effacée, puis réenregistrée en scannant le tag sur place avec un téléphone connecté.`,
      confirmLabel: 'Recalibrer',
      action: () => this.tagService.update(t.id, t.wingId, null, null)
    }).subscribe(() => this.reload());
  }

  copyUrl(t: Tag): void {
    navigator.clipboard?.writeText(this.tagUrl(t));
    this.copied = t.id;
    setTimeout(() => { if (this.copied === t.id) { this.copied = null; } }, 1500);
  }

  private fail(e: any): void {
    this.loading = false;
    this.error = errorMessage(e, 'Une erreur est survenue.');
    if (this.errorTimer) { clearTimeout(this.errorTimer); }
    this.errorTimer = setTimeout(() => this.error = null, 5000);
  }
}
