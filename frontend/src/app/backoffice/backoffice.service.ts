import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PresenceView, Stats } from './backoffice.models';

/** Appels API du back-office (consultation des interventions). */
@Injectable({ providedIn: 'root' })
export class BackofficeService {

  private readonly base = '/api/backoffice';

  constructor(private http: HttpClient) {}

  presences(): Observable<PresenceView[]> {
    return this.http.get<PresenceView[]>(`${this.base}/presences`);
  }

  stats(): Observable<Stats> {
    return this.http.get<Stats>(`${this.base}/stats`);
  }
}
