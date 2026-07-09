import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PresenceView } from './backoffice.models';

/** Appels API du back-office (consultation des interventions). */
@Injectable({ providedIn: 'root' })
export class BackofficeService {

  constructor(private http: HttpClient) {}

  presences(): Observable<PresenceView[]> {
    return this.http.get<PresenceView[]>('/api/presences');
  }
}
