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
  copied: number | null = null;

  // formulaires
  newBusiness = { name: '', bce: '' };
  newBuilding = { name: '', projectCode: '', buildingType: '', street: '', number: '', postalCode: '', city: '' };
  newWing = { buildingId: null as number | null, name: '' };
  newTag = { wingId: null as number | null, name: '' };

  constructor(private api: AdminService) {}

  ngOnInit(): void {
    this.reloadAll();
  }

  reloadAll(): void {
    this.api.businesses().subscribe({ next: (d) => this.businesses = d, error: () => this.fail() });
    this.api.buildings().subscribe({ next: (d) => this.buildings = d, error: () => this.fail() });
    this.api.wings().subscribe({ next: (d) => this.wings = d, error: () => this.fail() });
    this.api.tags().subscribe({ next: (d) => this.tags = d, error: () => this.fail() });
  }

  // ---- Sociétés ----
  addBusiness(): void {
    if (!this.newBusiness.name.trim()) { return; }
    this.api.createBusiness(this.newBusiness.name.trim(), this.newBusiness.bce.trim()).subscribe({
      next: () => { this.newBusiness = { name: '', bce: '' }; this.api.businesses().subscribe(d => this.businesses = d); },
      error: (e) => this.fail(e)
    });
  }
  removeBusiness(id: number): void {
    this.api.deleteBusiness(id).subscribe({ next: () => this.businesses = this.businesses.filter(b => b.id !== id) });
  }

  // ---- Bâtiments ----
  addBuilding(): void {
    if (!this.newBuilding.name.trim()) { return; }
    this.api.createBuilding({ ...this.newBuilding }).subscribe({
      next: () => {
        this.newBuilding = { name: '', projectCode: '', buildingType: '', street: '', number: '', postalCode: '', city: '' };
        this.api.buildings().subscribe(d => this.buildings = d);
      },
      error: (e) => this.fail(e)
    });
  }
  removeBuilding(id: number): void {
    this.api.deleteBuilding(id).subscribe({ next: () => this.buildings = this.buildings.filter(b => b.id !== id) });
  }

  // ---- Ailes ----
  addWing(): void {
    if (!this.newWing.buildingId || !this.newWing.name.trim()) { return; }
    this.api.createWing(this.newWing.buildingId, this.newWing.name.trim()).subscribe({
      next: () => { this.newWing = { buildingId: null, name: '' }; this.api.wings().subscribe(d => this.wings = d); },
      error: (e) => this.fail(e)
    });
  }
  removeWing(id: number): void {
    this.api.deleteWing(id).subscribe({ next: () => this.wings = this.wings.filter(w => w.id !== id) });
  }

  // ---- Tags ----
  addTag(): void {
    if (!this.newTag.wingId || !this.newTag.name.trim()) { return; }
    this.api.createTag(this.newTag.wingId, this.newTag.name.trim()).subscribe({
      next: () => { this.newTag = { wingId: null, name: '' }; this.api.tags().subscribe(d => this.tags = d); },
      error: (e) => this.fail(e)
    });
  }
  removeTag(id: number): void {
    this.api.deleteTag(id).subscribe({ next: () => this.tags = this.tags.filter(t => t.id !== id) });
  }

  copyUrl(t: TagAdmin): void {
    navigator.clipboard?.writeText(t.url);
    this.copied = t.id;
    setTimeout(() => { if (this.copied === t.id) { this.copied = null; } }, 1500);
  }

  private fail(e?: any): void {
    this.error = e?.error?.message ?? 'Une erreur est survenue.';
    setTimeout(() => this.error = null, 4000);
  }
}
