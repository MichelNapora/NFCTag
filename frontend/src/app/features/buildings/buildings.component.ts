import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BuildingService } from './building.service';
import { Building } from './building.models';
import { WingService } from '../wings/wing.service';
import { Wing } from '../wings/wing.models';
import { TagService } from '../tags/tag.service';
import { Tag } from '../tags/tag.models';
import { CountsService } from '../../common/shell/counts.service';
import { AuthService } from '../../common/auth/auth.service';
import { errorMessage } from '../../common/utils/http-error';
import {ConfirmService} from '../../common/confirm/confirm.service';

interface BuildingForm {
  id: string | null;
  name: string;
  projectCode: string;
  street: string;
  number: number | null;
  box: string;
  postalCode: number | null;
  city: string;
}

const EMPTY_FORM: BuildingForm = { id: null, name: '', projectCode: '', street: '', number: null, box: '', postalCode: null, city: '' };

@Component({
  selector: 'app-buildings',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './buildings.component.html',
  styleUrl: './buildings.component.scss'
})
export class BuildingsComponent implements OnInit {

  buildings: Building[] = [];
  wings: Wing[] = [];
  tags: Tag[] = [];
  loading = true;
  idsInUse: string[] = [];
  error: string | null = null;
  private errorTimer: ReturnType<typeof setTimeout> | null = null;
  query = '';

  modalOpen = false;
  form: BuildingForm = { ...EMPTY_FORM };
  saving = false;

  constructor(
    private buildingService: BuildingService,
    private wingService: WingService,
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
    this.buildingService.idsInUse().subscribe({ next: (d) => this.idsInUse = d });

    this.buildingService.findAll().subscribe({
      next: (d) => { this.buildings = d; this.loading = false; },
      error: (e) => this.fail(e)
    });
    this.wingService.findAll().subscribe({ next: (d) => this.wings = d });
    this.tagService.findAll().subscribe({ next: (d) => this.tags = d });
  }

  get filtered(): Building[] {
    const q = this.query.trim().toLowerCase();
    return this.buildings.filter(b =>
      !q ||
      b.name.toLowerCase().includes(q) ||
      b.projectCode.toLowerCase().includes(q) ||
      b.address.city.toLowerCase().includes(q)
    );
  }

  wingCount(buildingId: string): number {
    return this.wings.filter(w => w.buildingId === buildingId).length;
  }

  tagCount(buildingId: string): number {
    const wingIds = this.wings.filter(w => w.buildingId === buildingId).map(w => w.id);
    return this.tags.filter(t => wingIds.includes(t.wingId)).length;
  }

  openCreate(): void {
    this.form = { ...EMPTY_FORM };
    this.modalOpen = true;
  }

  openEdit(b: Building): void {
    this.form = {
      id: b.id,
      name: b.name,
      projectCode: b.projectCode,
      street: b.address.street,
      number: b.address.number,
      box: b.address.box ?? '',
      postalCode: b.address.postalCode,
      city: b.address.city
    };
    this.modalOpen = true;
  }

  close(): void {
    this.modalOpen = false;
  }

  get canSave(): boolean {
    const f = this.form;
    return !!(f.name.trim() && f.projectCode.trim() && f.street.trim() && f.number && f.postalCode && f.city.trim());
  }

  save(): void {
    if (!this.canSave || this.saving) { return; }
    this.saving = true;
    const f = this.form;
    const address = {
      street: f.street.trim(),
      number: f.number!,
      box: f.box.trim() || null,
      postalCode: f.postalCode!,
      city: f.city.trim()
    };
    const call = f.id
      ? this.buildingService.update(f.id, f.name.trim(), f.projectCode.trim(), address)
      : this.buildingService.create(f.name.trim(), f.projectCode.trim(), address);

    call.subscribe({
      next: () => { this.saving = false; this.modalOpen = false; this.reload(); this.counts.refresh(); },
      error: (e) => { this.saving = false; this.fail(e); }
    });
  }

  canDelete(id: string): boolean {
    return !this.idsInUse.includes(id);
  }

  remove(b: Building): void {
    this.confirm.ask({
      title: 'Supprimer le bâtiment',
      message: `Voulez-vous supprimer « ${b.name} » ?`,
      danger: true,
      action: () => this.buildingService.delete(b.id)
    }).subscribe(() => { this.reload(); this.counts.refresh(); });
  }

  private fail(e: any): void {
    this.loading = false;
    this.error = errorMessage(e, 'Une erreur est survenue.');
    if (this.errorTimer) { clearTimeout(this.errorTimer); }
    this.errorTimer = setTimeout(() => this.error = null, 5000);
  }
}
