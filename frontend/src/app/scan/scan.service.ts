import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ScanResult } from './scan.models';

/** Appels API du flux de scan (côté technicien). */
@Injectable({ providedIn: 'root' })
export class ScanService {

  private readonly base = '/api/scan';

  constructor(private http: HttpClient) {}

  scan(tagToken: string, deviceToken: string | null): Observable<ScanResult> {
    return this.http.post<ScanResult>(this.base, { tagToken, deviceToken });
  }

  lookup(tagToken: string, mobile: string): Observable<ScanResult> {
    return this.http.post<ScanResult>(`${this.base}/lookup`, { tagToken, mobile });
  }

  register(tagToken: string, mobile: string, businessId: number,
           firstname: string, lastname: string): Observable<ScanResult> {
    return this.http.post<ScanResult>(`${this.base}/register`,
      { tagToken, mobile, businessId, firstname, lastname });
  }
}
