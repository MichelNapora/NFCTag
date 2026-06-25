import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BackofficeService } from './backoffice.service';
import { PresenceView, Stats } from './backoffice.models';

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
  stats: Stats | null = null;
  presences: PresenceView[] = [];

  constructor(private api: BackofficeService) {}

  ngOnInit(): void {
    this.reload();
  }

  reload(): void {
    this.loading = true;
    this.error = null;
    this.api.stats().subscribe({
      next: (s) => { this.stats = s; },
      error: () => { this.error = 'Impossible de charger les statistiques.'; }
    });
    this.api.presences().subscribe({
      next: (p) => { this.presences = p; this.loading = false; },
      error: () => { this.error = 'Impossible de charger les interventions.'; this.loading = false; }
    });
  }

  formatDuration(minutes: number | null): string {
    if (minutes == null) { return '—'; }
    const h = Math.floor(minutes / 60);
    const m = minutes % 60;
    return h > 0 ? `${h}h${m.toString().padStart(2, '0')}` : `${m} min`;
  }
}
