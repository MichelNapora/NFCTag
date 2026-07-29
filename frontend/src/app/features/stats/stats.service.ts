import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TechnicianStats } from './stats.models';

@Injectable({ providedIn: 'root' })
export class StatsService {

  constructor(private http: HttpClient) {}

  byTechnician(): Observable<TechnicianStats[]> {
    return this.http.get<TechnicianStats[]>('/api/stats/technicians');
  }
}
