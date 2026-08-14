import { Component, DestroyRef, OnInit, inject } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { interval } from 'rxjs';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PresenceService } from '../presences/presence.service';
import { PresenceView, SearchMeta } from '../presences/presence.models';
import { formatDuration } from '../../common/utils/duration-formatter';
import { LOCATION_LABEL, locationLabelOf } from '../location/location.models';
import { AuthService } from '../../common/auth/auth.service';
import { errorMessage } from '../../common/utils/http-error';
import { downloadCsv, csvDate, csvToday } from '../../common/utils/csv-export';
import {ConfirmService} from '../../common/confirm/confirm.service';
import { CountsService } from '../../common/shell/counts.service';
import {
  CONFIRM_DELETE_PRESENCE, EXPORT_FAILED, BUTTON_DELETE, TITLE_DELETE_PRESENCE,
  DEPARTURE_DISTANCE_LABEL, DISTANCE_LABEL, INTERVENTIONS_COUNT, MSG, NO_LOCATION, PAGE_LABEL,
  INTERVENTIONS_LOAD_FAILED, TECHNICIAN, MOBILE, BUSINESS, BUILDING, WING, ARRIVAL, DEPARTURE,
  ESTIMATED, ANSWER_YES, ANSWER_NO, CSV_DURATION_MINUTES, CSV_LOCATION_STATUS,
  CSV_DISTANCE_METERS, CSV_DEPARTURE_LOCATION_STATUS, CSV_DEPARTURE_DISTANCE_METERS
} from '../../common/messages';
import { format } from '../../common/utils/format';
import { mobileDisplay } from '../../common/utils/mobile-display';
import {PresenceStateComponent} from '../presences/presence-state.component';

type Filter = 'all' | 'ongoing' | 'done' | 'estimated' | 'suspect';

const PAGE_SIZE = 20;

/** Les interventions arrivent des scans : on relit la page affichee. */
const LIST_REFRESH_MS = 60_000;

@Component({
  selector: 'app-interventions',
  standalone: true,
  imports: [CommonModule, FormsModule, PresenceStateComponent],
  templateUrl: './interventions.component.html',
  styleUrl: './interventions.component.scss'
})
export class InterventionsComponent implements OnInit {

  private destroyRef = inject(DestroyRef);

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

    interval(LIST_REFRESH_MS)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(() => this.reload(true));
  }

  /** Recharge la page courante et les compteurs. En mode silencieux, sans voile de chargement. */
  reload(silencieux = false): void {
    if (!silencieux) { this.loading = true; }
    this.error = null;

    this.presenceService.search(this.year, this.filter, this.query, this.page, PAGE_SIZE).subscribe({
      next: (p) => {
        this.rows = p.content;
        this.totalPages = p.page.totalPages;
        this.totalElements = p.page.totalElements;
        this.loading = false;
      },
      error: () => { this.error = INTERVENTIONS_LOAD_FAILED; this.loading = false; }
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

  duration(minutes: number | null): string {
    return formatDuration(minutes);
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
    const header = [TECHNICIAN, MOBILE, BUSINESS, BUILDING, WING,
      ARRIVAL, DEPARTURE, CSV_DURATION_MINUTES, ESTIMATED,
      CSV_LOCATION_STATUS, CSV_DISTANCE_METERS, CSV_DEPARTURE_LOCATION_STATUS, CSV_DEPARTURE_DISTANCE_METERS];

    const rows = all.map(p => [
      p.technicianName,
      mobileDisplay(p.mobile),
      p.businessName,
      p.buildingName,
      p.wingName,
      csvDate(p.arrivedAt),
      csvDate(p.departedAt),
      p.durationMinutes != null ? String(p.durationMinutes) : '',
      p.estimated ? ANSWER_YES : ANSWER_NO,
      locationLabelOf(p.locationStatus),
      p.distanceMeters != null ? String(Math.round(p.distanceMeters)) : '',
      this.departureLocationLabel(p),
      p.departureDistanceMeters != null ? String(Math.round(p.departureDistanceMeters)) : ''
    ]);

    downloadCsv(`interventions-${csvToday()}.csv`, header, rows);
  }
}
