import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import {PresenceView, PresencePage, SearchMeta} from './presence.models';

/** Consultation des interventions. */
@Injectable({ providedIn: 'root' })
export class PresenceService {

  constructor(private http: HttpClient) {}

  findAll(): Observable<PresenceView[]> {
    return this.http.get<PresenceView[]>('/api/presences');
  }

  /** Une page d'interventions filtrées. */
  search(year: number | null, state: string, query: string, page: number, size: number): Observable<PresencePage> {
    return this.http.get<PresencePage>('/api/presences/search', {
      params: this.filterParams(year, state, query)
        .set('page', page)
        .set('size', size)
    });
  }

  /** Compteurs des pastilles et années disponibles. */
  searchMeta(year: number | null, query: string): Observable<SearchMeta> {
    return this.http.get<SearchMeta>('/api/presences/search-meta', {
      params: this.filterParams(year, null, query)
    });
  }

  /** Toutes les lignes correspondant aux filtres, pour l'export. */
  export(year: number | null, state: string, query: string): Observable<PresenceView[]> {
    return this.http.get<PresenceView[]>('/api/presences/export', {
      params: this.filterParams(year, state, query)
    });
  }

  /** Construit les paramètres communs, en omettant ceux qui sont vides. */
  private filterParams(year: number | null, state: string | null, query: string): HttpParams {
    let params = new HttpParams();
    if (year != null) { params = params.set('year', year); }
    if (state && state !== 'all') { params = params.set('state', state); }
    if (query && query.trim()) { params = params.set('query', query.trim()); }
    return params;
  }

  delete(id: string): Observable<void> {
    return this.http.delete<void>(`/api/presences/${id}`);
  }
}
