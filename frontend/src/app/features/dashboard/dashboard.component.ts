import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PresenceService } from '../presences/presence.service';
import { StatsService } from '../stats/stats.service';
import { TechnicianStats } from '../stats/stats.models';
import { formatDuration } from '../../common/utils/duration-formatter';
import { LOCATION_LABEL } from '../location/location.models';
import {PresenceView} from '../presences/presence.models';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit {

  loading = true;
  error: string | null = null;
  presences: PresenceView[] = [];
  recent: PresenceView[] = [];

  totalPassages = 0;
  totalMinutes = 0;
  ongoing = 0;
  estimated = 0;
  unverified = 0;

  stats: TechnicianStats[] = [];

  constructor(
    private presenceService: PresenceService,
    private statsService: StatsService
  ) {}

  ngOnInit(): void {
    this.reload();
  }

  reload(): void {
    this.loading = true;
    this.error = null;
    this.presenceService.findAll().subscribe({
      next: (list) => {
        this.presences = list;
        this.totalPassages = list.length;
        this.totalMinutes = list.reduce((sum, p) => sum + (p.durationMinutes ?? 0), 0);
        this.ongoing = list.filter(p => p.departedAt === null).length;
        this.estimated = list.filter(p => p.estimated).length;
        this.unverified = list.filter(p => p.locationStatus === 'TOO_FAR').length;
        this.recent = [...list]
          .sort((a, b) => b.arrivedAt.localeCompare(a.arrivedAt))
          .slice(0, 8);
        this.loading = false;
      },
      error: () => { this.error = 'Impossible de charger les interventions.'; this.loading = false; }
    });
    this.statsService.byTechnician().subscribe({
      next: (list) => { this.stats = list; },
      error: () => { /* le tableau reste vide, pas bloquant */ }
    });
  }

  isOngoing(p: PresenceView): boolean {
    return p.departedAt === null;
  }

  duration(minutes: number | null): string {
    return formatDuration(minutes);
  }

  locationLabel(p: PresenceView): string {
    return p.locationStatus ? LOCATION_LABEL[p.locationStatus] : 'Non renseigné';
  }

  /** Un taux bas signale un technicien scanne sans GPS. */
  isLowRate(s: TechnicianStats): boolean {
    return s.locatedRate != null && s.locatedRate < 50;
  }

}
