import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { AdminService } from './admin.service';
import { BuildingAdmin, BusinessAdmin, TagAdmin, WingAdmin } from './admin.models';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.scss'
})
export class AdminComponent implements OnInit {

  businesses: BusinessAdmin[] = [];
  buildings: BuildingAdmin[] = [];
  wings: WingAdmin[] = [];
  tags: TagAdmin[] = [];
  error: string | null = null;
  copied: string | null = null;

  // formulaires
  newBusiness = { name: '', bce: '' };
  newBuilding = { name: '', projectCode: '', street: '', number: null as number | null, box: '', postalCode: null as number | null, city: '' };
  newWing = { buildingId: null as string | null, name: '' };
  newTag = { wingId: null as string | null, latitude: null as number | null, longitude: null as number | null };

  constructor(private api: AdminService) {}

  ngOnInit(): void {
    this.reloadAll();
  }

  reloadAll(): void {
    this.api.businesses().subscribe({ next: (d) => this.businesses = d, error: (e) => this.fail(e) });
    this.api.buildings().subscribe({ next: (d) => this.buildings = d, error: (e) => this.fail(e) });
    this.api.wings().subscribe({ next: (d) => this.wings = d, error: (e) => this.fail(e) });
    this.api.tags().subscribe({ next: (d) => this.tags = d, error: (e) => this.fail(e) });
  }

  // ---- Jointures d'affichage (les DTO ne portent que les ids) ----
  buildingName(buildingId: string): string {
    return this.buildings.find(b => b.id === buildingId)?.name ?? '—';
  }
  wingLabel(wingId: string): string {
    const wing = this.wings.find(w => w.id === wingId);
    return wing ? `${this.buildingName(wing.buildingId)} / ${wing.name}` : '—';
  }
  /** URL à encoder sur le tag physique : la page de scan du front. */
  tagUrl(t: TagAdmin): string {
    return `${location.origin}/scan/${t.scanToken}`;
  }

  // ---- Sociétés ----
  addBusiness(): void {
    if (!this.newBusiness.name.trim() || !this.newBusiness.bce.trim()) { return; }
    this.api.createBusiness(this.newBusiness.name.trim(), this.newBusiness.bce.trim()).subscribe({
      next: () => { this.newBusiness = { name: '', bce: '' }; this.api.businesses().subscribe(d => this.businesses = d); },
      error: (e) => this.fail(e)
    });
  }
  removeBusiness(id: string): void {
    this.api.deleteBusiness(id).subscribe({
      next: () => this.businesses = this.businesses.filter(b => b.id !== id),
      error: (e) => this.fail(e)
    });
  }

  // ---- Bâtiments ----
  get canAddBuilding(): boolean {
    const b = this.newBuilding;
    return !!(b.name.trim() && b.projectCode.trim() && b.street.trim() && b.number && b.postalCode && b.city.trim());
  }
  addBuilding(): void {
    if (!this.canAddBuilding) { return; }
    const b = this.newBuilding;
    this.api.createBuilding(b.name.trim(), b.projectCode.trim(), {
      street: b.street.trim(),
      number: b.number!,
      box: b.box.trim() || null,
      postalCode: b.postalCode!,
      city: b.city.trim()
    }).subscribe({
      next: () => {
        this.newBuilding = { name: '', projectCode: '', street: '', number: null, box: '', postalCode: null, city: '' };
        this.api.buildings().subscribe(d => this.buildings = d);
      },
      error: (e) => this.fail(e)
    });
  }
  removeBuilding(id: string): void {
    this.api.deleteBuilding(id).subscribe({
      next: () => this.buildings = this.buildings.filter(b => b.id !== id),
      error: (e) => this.fail(e)
    });
  }

  // ---- Ailes ----
  addWing(): void {
    if (!this.newWing.buildingId || !this.newWing.name.trim()) { return; }
    this.api.createWing(this.newWing.name.trim(), this.newWing.buildingId).subscribe({
      next: () => { this.newWing = { buildingId: null, name: '' }; this.api.wings().subscribe(d => this.wings = d); },
      error: (e) => this.fail(e)
    });
  }
  removeWing(id: string): void {
    this.api.deleteWing(id).subscribe({
      next: () => this.wings = this.wings.filter(w => w.id !== id),
      error: (e) => this.fail(e)
    });
  }

  // ---- Tags ----
  addTag(): void {
    if (!this.newTag.wingId) { return; }
    this.api.createTag(this.newTag.wingId, this.newTag.latitude, this.newTag.longitude).subscribe({
      next: () => { this.newTag = { wingId: null, latitude: null, longitude: null }; this.api.tags().subscribe(d => this.tags = d); },
      error: (e) => this.fail(e)
    });
  }
  removeTag(id: string): void {
    this.api.deleteTag(id).subscribe({
      next: () => this.tags = this.tags.filter(t => t.id !== id),
      error: (e) => this.fail(e)
    });
  }

  copyUrl(t: TagAdmin): void {
    navigator.clipboard?.writeText(this.tagUrl(t));
    this.copied = t.id;
    setTimeout(() => { if (this.copied === t.id) { this.copied = null; } }, 1500);
  }

  private fail(e?: any): void {
    const body = e?.error;
    this.error = typeof body === 'string' && body
      ? body
      : body?.message ?? 'Une erreur est survenue.';
    setTimeout(() => this.error = null, 4000);
  }
}
