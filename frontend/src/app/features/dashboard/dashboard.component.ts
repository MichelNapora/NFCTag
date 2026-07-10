import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PresenceService } from '../presences/presence.service';
import { PresenceView } from '../presences/presence.models';
import { formatDuration } from '../../common/duration-formatter';

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

  constructor(private presenceService: PresenceService) {}

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
        this.unverified = list.filter(p => !p.locationVerified).length;
        this.recent = [...list]
          .sort((a, b) => b.arrivedAt.localeCompare(a.arrivedAt))
          .slice(0, 8);
        this.loading = false;
      },
      error: () => { this.error = 'Impossible de charger les interventions.'; this.loading = false; }
    });
  }

  isOngoing(p: PresenceView): boolean {
    return p.departedAt === null;
  }

  duration(minutes: number | null): string {
    return formatDuration(minutes);
  }
}
