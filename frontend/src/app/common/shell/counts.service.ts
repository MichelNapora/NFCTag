import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, forkJoin } from 'rxjs';

/** Compteurs affichés dans la barre latérale. */
export interface Counts {
  presences: number;
  buildings: number;
  wings: number;
  tags: number;
  businesses: number;
}

const EMPTY: Counts = { presences: 0, buildings: 0, wings: 0, tags: 0, businesses: 0 };

@Injectable({ providedIn: 'root' })
export class CountsService {

  readonly counts$ = new BehaviorSubject<Counts>(EMPTY);

  constructor(private http: HttpClient) {}

  /** Recharge tous les compteurs (appelé au démarrage et après chaque ajout/suppression). */
  refresh(): void {
    forkJoin({
      presences: this.http.get<unknown[]>('/api/presences'),
      buildings: this.http.get<unknown[]>('/api/buildings'),
      wings: this.http.get<unknown[]>('/api/wings'),
      tags: this.http.get<unknown[]>('/api/tags'),
      businesses: this.http.get<unknown[]>('/api/businesses')
    }).subscribe({
      next: (r) => this.counts$.next({
        presences: r.presences.length,
        buildings: r.buildings.length,
        wings: r.wings.length,
        tags: r.tags.length,
        businesses: r.businesses.length
      }),
      error: () => this.counts$.next(EMPTY)
    });
  }
}
