import { AfterViewInit, Component, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { forkJoin } from 'rxjs';
import * as L from 'leaflet';
import { TagService } from '../tags/tag.service';
import { Tag } from '../tags/tag.models';
import { WingService } from '../wings/wing.service';
import { Wing } from '../wings/wing.models';
import { BuildingService } from '../buildings/building.service';
import { Building } from '../buildings/building.models';
import { PresenceService } from '../presences/presence.service';
import { PresenceView } from '../presences/presence.models';

/** Fréquence de rafraîchissement de l'activité (ms). */
const REFRESH_MS = 60000;

@Component({
  selector: 'app-map',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './map.component.html',
  styleUrl: './map.component.scss'
})
export class MapComponent implements AfterViewInit, OnDestroy {

  error: string | null = null;
  located: Tag[] = [];
  uncalibrated: Tag[] = [];
  ongoingTotal = 0;
  lastRefresh: Date | null = null;

  private map: L.Map | null = null;
  private markers: L.LayerGroup | null = null;
  private wings: Wing[] = [];
  private buildings: Building[] = [];
  private timer: ReturnType<typeof setInterval> | null = null;
  private fitted = false;

  constructor(
    private tagService: TagService,
    private wingService: WingService,
    private buildingService: BuildingService,
    private presenceService: PresenceService
  ) {}

  ngAfterViewInit(): void {
    this.map = L.map('tags-map', { scrollWheelZoom: true });
    // Fond de carte OpenStreetMap — gratuit, sans clé
    L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 19,
      attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    }).addTo(this.map);
    this.map.setView([50.6451, 5.5734], 12); // Liège par défaut
    this.markers = L.layerGroup().addTo(this.map);

    this.reload();
    this.timer = setInterval(() => this.reload(), REFRESH_MS);
  }

  ngOnDestroy(): void {
    if (this.timer) { clearInterval(this.timer); }
    this.map?.remove();
  }

  reload(): void {
    forkJoin({
      tags: this.tagService.findAll(),
      wings: this.wingService.findAll(),
      buildings: this.buildingService.findAll(),
      presences: this.presenceService.findAll()
    }).subscribe({
      next: ({ tags, wings, buildings, presences }) => {
        this.wings = wings;
        this.buildings = buildings;
        this.located = tags.filter(t => t.latitude != null && t.longitude != null);
        this.uncalibrated = tags.filter(t => t.latitude == null || t.longitude == null);
        this.draw(presences);
        this.lastRefresh = new Date();
        this.error = null;
      },
      error: () => this.error = 'Impossible de charger la carte.'
    });
  }

  wingLabel(wingId: string): string {
    const wing = this.wings.find(w => w.id === wingId);
    if (!wing) { return '—'; }
    const building = this.buildings.find(b => b.id === wing.buildingId);
    return `${building?.name ?? '—'} / ${wing.name}`;
  }

  private ongoingCount(tag: Tag, presences: PresenceView[]): number {
    const wing = this.wings.find(w => w.id === tag.wingId);
    const building = this.buildings.find(b => b.id === wing?.buildingId);
    if (!wing || !building) { return 0; }
    return presences.filter(p =>
      p.departedAt === null && p.wingName === wing.name && p.buildingName === building.name
    ).length;
  }

  private draw(presences: PresenceView[]): void {
    if (!this.map || !this.markers) { return; }
    this.markers.clearLayers();
    this.ongoingTotal = presences.filter(p => p.departedAt === null).length;

    const bounds: L.LatLngTuple[] = [];
    for (const tag of this.located) {
      const ongoing = this.ongoingCount(tag, presences);
      const point: L.LatLngTuple = [tag.latitude!, tag.longitude!];
      bounds.push(point);

      const marker = L.circleMarker(point, {
        radius: 9,
        color: '#111111',
        weight: 2,
        fillColor: ongoing > 0 ? '#111111' : '#ffffff',
        fillOpacity: 1
      });
      marker.bindPopup(
        `<strong>${this.wingLabel(tag.wingId)}</strong><br>` +
        (ongoing > 0 ? `${ongoing} technicien(s) sur place` : 'Aucune intervention en cours')
      );
      marker.addTo(this.markers);
    }

    if (bounds.length > 0 && !this.fitted) {
      this.map.fitBounds(bounds, { padding: [40, 40], maxZoom: 16 });
      this.fitted = true;
    }
  }
}
