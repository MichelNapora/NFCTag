import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { StatsService } from '../stats/stats.service';
import { TechnicianStats, BusinessStats } from '../stats/stats.models';
import { PresenceView } from '../presences/presence.models';
import { formatDuration } from '../../common/utils/duration-formatter';
import { DASHBOARD_LOAD_FAILED, FAR_SCANS_ALERT, MSG } from '../../common/messages';
import { format } from '../../common/utils/format';
import { PresenceStateComponent } from '../presences/presence-state.component';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule, PresenceStateComponent],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit {

  loading = true;
  error: string | null = null;

  // Compteurs et dernières interventions, calculés par le serveur
  totalPassages = 0;
  totalMinutes = 0;
  ongoing = 0;
  estimated = 0;
  unverified = 0;

  readonly msg = MSG;

  /** Bandeau d'alerte : nombre d'interventions scannées trop loin du tag. */
  get farScansAlert(): string {
    return format(FAR_SCANS_ALERT, String(this.unverified));
  }
  recent: PresenceView[] = [];

  stats: TechnicianStats[] = [];
  businessStats: BusinessStats[] = [];
  technicianQuery = '';
  businessQuery = '';

  constructor(private statsService: StatsService) {}

  ngOnInit(): void {
    this.reload();
  }

  reload(): void {
    this.loading = true;
    this.error = null;

    this.statsService.dashboard().subscribe({
      next: (d) => {
        this.totalPassages = d.totalPassages;
        this.totalMinutes = d.totalMinutes;
        this.ongoing = d.ongoing;
        this.estimated = d.estimated;
        this.unverified = d.suspect;
        this.recent = d.recent;
        this.loading = false;
      },
      error: () => { this.error = DASHBOARD_LOAD_FAILED; this.loading = false; }
    });

    this.statsService.byTechnician().subscribe({
      next: (list) => { this.stats = list; },
      error: () => { /* pas bloquant */ }
    });

    this.statsService.byBusiness().subscribe({
      next: (list) => { this.businessStats = list; },
      error: () => { /* pas bloquant */ }
    });
  }

  duration(minutes: number | null): string {
    return formatDuration(minutes);
  }

  get filteredStats(): TechnicianStats[] {
    const q = this.technicianQuery.trim().toLowerCase();
    return this.stats.filter(s => !q || s.technicianName.toLowerCase().includes(q));
  }

  get filteredBusinessStats(): BusinessStats[] {
    const q = this.businessQuery.trim().toLowerCase();
    return this.businessStats.filter(s => !q || s.businessName.toLowerCase().includes(q));
  }

  isLowRate(s: TechnicianStats): boolean {
    return s.locatedRate != null && s.locatedRate < 50;
  }

  isLowBusinessRate(s: BusinessStats): boolean {
    return s.locatedRate != null && s.locatedRate < 50;
  }
}
