import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PresenceView, TechnicianStats } from './presence.models';

/** Consultation des interventions. */
@Injectable({ providedIn: 'root' })
export class PresenceService {

  constructor(private http: HttpClient) {}

  findAll(): Observable<PresenceView[]> {
    return this.http.get<PresenceView[]>('/api/presences');
  }
  technicianStats(): Observable<TechnicianStats[]> {
    return this.http.get<TechnicianStats[]>('/api/presences/technician-stats');
  }
}
