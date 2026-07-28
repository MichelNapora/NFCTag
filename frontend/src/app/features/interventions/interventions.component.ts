import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PresenceService } from '../presences/presence.service';
import { PresenceView } from '../presences/presence.models';
import { formatDuration } from '../../common/utils/duration-formatter';
import { LOCATION_LABEL } from '../../common/utils/location-status';
import { AuthService } from '../../common/auth/auth.service';
import { errorMessage } from '../../common/utils/http-error';


type Filter = 'all' | 'ongoing' | 'done' | 'estimated' | 'suspect';

@Component({
  selector: 'app-interventions',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './interventions.component.html',
  styleUrl: './interventions.component.scss'
})
export class InterventionsComponent implements OnInit {

  loading = true;
  error: string | null = null;
  all: PresenceView[] = [];
  query = '';
  filter: Filter = 'all';

  constructor(
    private presenceService: PresenceService,
    public auth: AuthService
  ) {}

  ngOnInit(): void {
    this.reload();
  }

  reload(): void {
    this.loading = true;
    this.error = null;
    this.presenceService.findAll().subscribe({
      next: (list) => {
        this.all = [...list].sort((a, b) => b.arrivedAt.localeCompare(a.arrivedAt));
        this.loading = false;
      },
      error: () => { this.error = 'Impossible de charger les interventions.'; this.loading = false; }
    });
  }

  isOngoing(p: PresenceView): boolean {
    return p.departedAt === null;
  }

  count(filter: Filter): number {
    return this.byFilter(filter).length;
  }

  setFilter(filter: Filter): void {
    this.filter = filter;
  }

  get filtered(): PresenceView[] {
    const q = this.query.trim().toLowerCase();
    return this.byFilter(this.filter).filter(p =>
      !q ||
      p.technicianName.toLowerCase().includes(q) ||
      p.mobile.toLowerCase().includes(q) ||
      p.businessName.toLowerCase().includes(q) ||
      p.buildingName.toLowerCase().includes(q) ||
      p.wingName.toLowerCase().includes(q)
    );
  }

  private byFilter(filter: Filter): PresenceView[] {
    if (filter === 'ongoing') { return this.all.filter(p => this.isOngoing(p)); }
    if (filter === 'done') { return this.all.filter(p => !this.isOngoing(p) && !p.estimated); }
    if (filter === 'estimated') { return this.all.filter(p => p.estimated); }
    if (filter === 'suspect') { return this.all.filter(p => p.locationStatus === 'TOO_FAR'); }
    return this.all;
  }

  duration(minutes: number | null): string {
    return formatDuration(minutes);
  }

  locationLabel(p: PresenceView): string {
    return p.locationStatus ? LOCATION_LABEL[p.locationStatus] : 'Non renseigné';
  }

  remove(p: PresenceView): void {
    const quand = new Date(p.arrivedAt).toLocaleString('fr-BE');
    if (!confirm(`Supprimer l'intervention de ${p.technicianName} du ${quand} ?\nCette suppression est définitive.`)) { return; }
    this.presenceService.delete(p.id).subscribe({
      next: () => this.reload(),
      error: (e) => { this.error = errorMessage(e, 'Suppression impossible.'); }
    });
  }
}
