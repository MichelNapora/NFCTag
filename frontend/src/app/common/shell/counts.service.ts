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
  technicians: number;
}

const EMPTY: Counts = { presences: 0, buildings: 0, wings: 0, tags: 0, businesses: 0, technicians: 0 };

@Injectable({ providedIn: 'root' })
export class CountsService {

  readonly counts$ = new BehaviorSubject<Counts>(EMPTY);

  constructor(private http: HttpClient) {}

  /** Recharge tous les compteurs — la base compte, on ne télécharge plus les listes. */
  refresh(): void {
    forkJoin({
      presences: this.http.get<number>('/api/presences/count'),
      buildings: this.http.get<number>('/api/buildings/count'),
      wings: this.http.get<number>('/api/wings/count'),
      tags: this.http.get<number>('/api/tags/count'),
      businesses: this.http.get<number>('/api/businesses/count'),
      technicians: this.http.get<number>('/api/technicians/count')
    }).subscribe({
      next: (r) => this.counts$.next(r),
      error: () => this.counts$.next(EMPTY)
    });
  }
}
