import { Component, OnInit } from '@angular/core';
import { CommonModule, formatNumber } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PresenceService } from '../presences/presence.service';
import { PresenceView, SearchMeta } from '../presences/presence.models';
import { formatDuration } from '../../common/utils/duration-formatter';
import { LOCATION_LABEL } from '../location/location.models';
import { AuthService } from '../../common/auth/auth.service';
import { errorMessage } from '../../common/utils/http-error';
import { downloadCsv, csvDate, csvToday } from '../../common/utils/csv-export';
import {ConfirmService} from '../../common/confirm/confirm.service';
import { CountsService } from '../../common/shell/counts.service';
import {
  CONFIRM_DELETE_PRESENCE, EXPORT_FAILED, BUTTON_DELETE, TITLE_DELETE_PRESENCE,
  DEPARTURE_DISTANCE_LABEL, DISTANCE_LABEL, INTERVENTIONS_COUNT, MSG, NO_LOCATION, PAGE_LABEL
} from '../../common/messages';
import { format } from '../../common/utils/format';
import { mobileDisplay } from '../../common/utils/mobile-display';

type Filter = 'all' | 'ongoing' | 'done' | 'estimated' | 'suspect';

const PAGE_SIZE = 20;

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

  /** Les lignes de la page affichée — plus toute la base. */
  rows: PresenceView[] = [];

  /** Compteurs et années, calculés par le serveur. */
  meta: SearchMeta = { years: [], all: 0, ongoing: 0, done: 0, estimated: 0, suspect: 0 };

  query = '';
  filter: Filter = 'all';
  year: number | null = null;

  page = 0;
  totalPages = 0;
  totalElements = 0;

  exporting = false;
  private queryTimer: ReturnType<typeof setTimeout> | null = null;


  readonly msg = MSG;

  /** « à 120 m », sous le statut de localisation. */
  distanceLabel(p: PresenceView): string {
    return p.distanceMeters == null
      ? ''
      : format(DISTANCE_LABEL, formatNumber(p.distanceMeters, 'fr', '1.0-0'));
  }

  /** « à 5 271 m au départ », quand le départ a été scanné trop loin. */
  departureDistanceLabel(p: PresenceView): string {
    return p.departureDistanceMeters == null
      ? ''
      : format(DEPARTURE_DISTANCE_LABEL, formatNumber(p.departureDistanceMeters, 'fr', '1.0-0'));
  }

  /** Ligne de pagination, affichée seulement s'il y a plusieurs pages. */
  get pageLabel(): string {
    return format(PAGE_LABEL, String(this.page + 1), String(this.totalPages), String(this.totalElements));
  }

  /** Total affiché sous le tableau quand tout tient sur une page. */
  get totalLabel(): string {
    return format(INTERVENTIONS_COUNT, String(this.totalElements));
  }

  constructor(
    private presenceService: PresenceService,
    public auth: AuthService,
    private confirm: ConfirmService,
    private counts: CountsService
  ) {}

  ngOnInit(): void {
    this.reload();
  }

  /** Recharge la page courante et les compteurs. */
  reload(): void {
    this.loading = true;
    this.error = null;

    this.presenceService.search(this.year, this.filter, this.query, this.page, PAGE_SIZE).subscribe({
      next: (p) => {
        this.rows = p.content;
        this.totalPages = p.page.totalPages;
        this.totalElements = p.page.totalElements;
        this.loading = false;
      },
      error: () => { this.error = 'Impossible de charger les interventions.'; this.loading = false; }
    });

    this.presenceService.searchMeta(this.year, this.query).subscribe({
      next: (m) => this.meta = m,
      error: () => { /* les compteurs restent à zéro, pas bloquant */ }
    });
  }

  /** Tout changement de filtre ramène à la première page. */
  private reloadFromFirstPage(): void {
    this.page = 0;
    this.reload();
  }

  setFilter(filter: Filter): void {
    this.filter = filter;
    this.reloadFromFirstPage();
  }

  setYear(year: number | null): void {
    this.year = year;
    this.reloadFromFirstPage();
  }

  /** Attend 400 ms après la dernière frappe avant d'interroger le serveur. */
  onQueryChange(): void {
    if (this.queryTimer) { clearTimeout(this.queryTimer); }
    this.queryTimer = setTimeout(() => this.reloadFromFirstPage(), 400);
  }

  goToPage(page: number): void {
    if (page < 0 || page >= this.totalPages || page === this.page) { return; }
    this.page = page;
    this.reload();
  }

  get canPrevious(): boolean {
    return this.page > 0;
  }

  get canNext(): boolean {
    return this.page + 1 < this.totalPages;
  }

  /** Compteur d'une pastille, fourni par le serveur. */
  count(filter: Filter): number {
    return this.meta[filter];
  }

  isOngoing(p: PresenceView): boolean {
    return p.departedAt === null;
  }

  duration(minutes: number | null): string {
    return formatDuration(minutes);
  }

  locationLabel(p: PresenceView): string {
    return p.locationStatus ? LOCATION_LABEL[p.locationStatus] : NO_LOCATION;
  }

  /** Position d'où le départ a été scanné. Vide si départ estimé ou intervention en cours. */
  departureLocationLabel(p: PresenceView): string {
    return p.departureLocationStatus ? LOCATION_LABEL[p.departureLocationStatus] : '';
  }

  remove(p: PresenceView): void {
    const quand = new Date(p.arrivedAt).toLocaleString('fr-BE');
    this.confirm.ask({
      title: TITLE_DELETE_PRESENCE,
      message: format(CONFIRM_DELETE_PRESENCE, p.technicianName, quand),
      confirmLabel: BUTTON_DELETE,
      danger: true,
      action: () => this.presenceService.delete(p.id)
    }).subscribe(() => { this.reload(); this.counts.refresh(); });
  }

  /** Exporte TOUTES les lignes correspondant aux filtres, pas seulement la page. */
  exportCsv(): void {
    if (this.exporting) { return; }
    this.exporting = true;

    this.presenceService.export(this.year, this.filter, this.query).subscribe({
      next: (all) => {
        this.exporting = false;
        this.buildCsv(all);
      },
      error: (e) => {
        this.exporting = false;
        this.error = errorMessage(e, EXPORT_FAILED);
      }
    });
  }

  private buildCsv(all: PresenceView[]): void {
    const header = ['Technicien', 'Mobile', 'Société', 'Bâtiment', 'Aile',
      'Arrivée', 'Départ', 'Durée (min)', 'Estimé',
      'Statut localisation', 'Distance (m)','Statut localisation départ', 'Distance départ (m)'];

    const rows = all.map(p => [
      p.technicianName,
      mobileDisplay(p.mobile),
      p.businessName,
      p.buildingName,
      p.wingName,
      csvDate(p.arrivedAt),
      csvDate(p.departedAt),
      p.durationMinutes != null ? String(p.durationMinutes) : '',
      p.estimated ? 'Oui' : 'Non',
      this.locationLabel(p),
      p.distanceMeters != null ? String(Math.round(p.distanceMeters)) : '',
      this.departureLocationLabel(p),
      p.departureDistanceMeters != null ? String(Math.round(p.departureDistanceMeters)) : ''
    ]);

    downloadCsv(`interventions-${csvToday()}.csv`, header, rows);
  }
}
