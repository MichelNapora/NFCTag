import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { BackofficeService } from './backoffice.service';
import { PresenceView, StatRow, Stats } from './backoffice.models';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit {

  loading = true;
  error: string | null = null;
  stats: Stats | null = null;
  presences: PresenceView[] = [];

  constructor(private api: BackofficeService) {}

  ngOnInit(): void {
    this.reload();
  }

  reload(): void {
    this.loading = true;
    this.error = null;
    this.api.presences().subscribe({
      next: (p) => {
        this.presences = p;
        this.stats = this.computeStats(p);
        this.loading = false;
      },
      error: () => { this.error = 'Impossible de charger les interventions.'; this.loading = false; }
    });
  }

  isOngoing(p: PresenceView): boolean {
    return p.departedAt === null;
  }

  formatDuration(minutes: number | null): string {
    if (minutes == null) { return '—'; }
    const h = Math.floor(minutes / 60);
    const m = minutes % 60;
    return h > 0 ? `${h}h${m.toString().padStart(2, '0')}` : `${m} min`;
  }

  /** Synthèse calculée à partir de la liste des présences. */
  private computeStats(presences: PresenceView[]): Stats {
    return {
      totalPassages: presences.length,
      totalMinutes: presences.reduce((sum, p) => sum + (p.durationMinutes ?? 0), 0),
      ongoing: presences.filter(p => this.isOngoing(p)).length,
      estimated: presences.filter(p => p.estimated).length,
      byBusiness: this.groupBy(presences, p => p.businessName),
      byBuilding: this.groupBy(presences, p => p.buildingName)
    };
  }

  private groupBy(presences: PresenceView[], key: (p: PresenceView) => string): StatRow[] {
    const rows = new Map<string, StatRow>();
    for (const p of presences) {
      const label = key(p);
      const row = rows.get(label) ?? { label, passages: 0, totalMinutes: 0 };
      row.passages++;
      row.totalMinutes += p.durationMinutes ?? 0;
      rows.set(label, row);
    }
    return Array.from(rows.values()).sort((a, b) => b.passages - a.passages);
  }
}
