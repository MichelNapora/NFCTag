import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PresenceView } from './presence.models';

/** Consultation des interventions. */
@Injectable({ providedIn: 'root' })
export class PresenceService {

  constructor(private http: HttpClient) {}

  findAll(): Observable<PresenceView[]> {
    return this.http.get<PresenceView[]>('/api/presences');
  }
}
