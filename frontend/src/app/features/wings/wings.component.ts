import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { WingService } from './wing.service';
import { Wing } from './wing.models';
import { BuildingService } from '../buildings/building.service';
import { Building } from '../buildings/building.models';
import { TagService } from '../tags/tag.service';
import { Tag } from '../tags/tag.models';
import { CountsService } from '../../common/shell/counts.service';
import { AuthService } from '../../common/auth/auth.service';
import { errorMessage } from '../../common/utils/http-error';
import { ConfirmService } from '../../common/confirm/confirm.service';
import { CONFIRM_DELETE, GENERIC_ERROR, BUTTON_DELETE, TITLE_DELETE_WING } from '../../common/messages';
import { format } from '../../common/utils/format';

interface WingForm {
  id: string | null;
  name: string;
  buildingId: string | null;
}

@Component({
  selector: 'app-wings',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './wings.component.html',
  styleUrl: './wings.component.scss'
})
export class WingsComponent implements OnInit {

  wings: Wing[] = [];
  buildings: Building[] = [];
  tags: Tag[] = [];
  loading = true;
  idsInUse: string []=[];
  error: string | null = null;
  private errorTimer: ReturnType<typeof setTimeout> | null = null;
  query = '';

  modalOpen = false;
  form: WingForm = { id: null, name: '', buildingId: null };
  saving = false;

  constructor(
    private wingService: WingService,
    private buildingService: BuildingService,
    private tagService: TagService,
    private counts: CountsService,
    public auth: AuthService,
    private confirm: ConfirmService
  ) {}

  ngOnInit(): void {
    this.reload();
  }

  reload(): void {
    this.loading = true;
    this.wingService.idsInUse().subscribe({ next: (d) => this.idsInUse = d });

    this.wingService.findAll().subscribe({
      next: (d) => { this.wings = d; this.loading = false; },
      error: (e) => this.fail(e)
    });
    this.buildingService.findAll().subscribe({ next: (d) => this.buildings = d });
    this.tagService.findAll().subscribe({ next: (d) => this.tags = d });
  }

  buildingName(buildingId: string): string {
    return this.buildings.find(b => b.id === buildingId)?.name ?? '—';
  }

  hasTag(wingId: string): boolean {
    return this.tags.some(t => t.wingId === wingId);
  }

  get filtered(): Wing[] {
    const q = this.query.trim().toLowerCase();
    return this.wings.filter(w =>
      !q ||
      w.name.toLowerCase().includes(q) ||
      this.buildingName(w.buildingId).toLowerCase().includes(q)
    );
  }

  openCreate(): void {
    this.form = { id: null, name: '', buildingId: null };
    this.modalOpen = true;
  }

  openEdit(w: Wing): void {
    this.form = { id: w.id, name: w.name, buildingId: w.buildingId };
    this.modalOpen = true;
  }

  close(): void {
    this.modalOpen = false;
  }

  get canSave(): boolean {
    return !!(this.form.name.trim() && this.form.buildingId);
  }

  save(): void {
    if (!this.canSave || this.saving) { return; }
    this.saving = true;
    const f = this.form;
    const call = f.id
      ? this.wingService.update(f.id, f.name.trim(), f.buildingId!)
      : this.wingService.create(f.name.trim(), f.buildingId!);

    call.subscribe({
      next: () => { this.saving = false; this.modalOpen = false; this.reload(); this.counts.refresh(); },
      error: (e) => { this.saving = false; this.fail(e); }
    });
  }

  canDelete(id: string): boolean {
    return !this.idsInUse.includes(id);
  }


  remove(w: Wing): void {
    this.confirm.ask({
      title: TITLE_DELETE_WING,
      message: format(CONFIRM_DELETE, w.name),
      confirmLabel: BUTTON_DELETE,
      danger: true,
      action: () => this.wingService.delete(w.id)
    }).subscribe(() => { this.reload(); this.counts.refresh(); });
  }

  private fail(e: any): void {
    this.loading = false;
    this.error = errorMessage(e, GENERIC_ERROR)
    if (this.errorTimer) { clearTimeout(this.errorTimer); }
    this.errorTimer = setTimeout(() => this.error = null, 5000);
  }
}
