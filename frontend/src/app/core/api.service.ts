import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PresenceView, ScanResult, Stats } from './models';

@Injectable({ providedIn: 'root' })
export class ApiService {

  private readonly base = '/api';

  constructor(private http: HttpClient) {}

  // ---- Scan (technicien) ----

  scan(tagToken: string, deviceToken: string | null): Observable<ScanResult> {
    return this.http.post<ScanResult>(`${this.base}/scan`, { tagToken, deviceToken });
  }

  lookup(tagToken: string, mobile: string): Observable<ScanResult> {
    return this.http.post<ScanResult>(`${this.base}/scan/lookup`, { tagToken, mobile });
  }

  register(tagToken: string, mobile: string, businessId: number,
           firstname: string, lastname: string): Observable<ScanResult> {
    return this.http.post<ScanResult>(`${this.base}/scan/register`,
      { tagToken, mobile, businessId, firstname, lastname });
  }

  // ---- Back-office ----

  presences(): Observable<PresenceView[]> {
    return this.http.get<PresenceView[]>(`${this.base}/backoffice/presences`);
  }

  stats(): Observable<Stats> {
    return this.http.get<Stats>(`${this.base}/backoffice/stats`);
  }
}
