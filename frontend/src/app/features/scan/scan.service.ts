import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Business, CalibratedTag, ScanRequest, ScanResponse, TagPosition} from './scan.models';

/** Appels API du flux de scan (côté technicien). */
@Injectable({ providedIn: 'root' })
export class ScanService {

  constructor(private http: HttpClient) {}

  scan(scanToken: string, request: ScanRequest): Observable<ScanResponse> {
    return this.http.post<ScanResponse>(`/api/scan/${scanToken}`, request);
  }

  businesses(): Observable<Business[]> {
    return this.http.get<Business[]>('/api/businesses');
  }


  changeBusiness(deviceToken: string, businessId: string): Observable<void> {
    return this.http.post<void>('/api/scan/technician/business', { deviceToken, businessId });
  }

  calibrate(scanToken: string, position: TagPosition): Observable<CalibratedTag> {
    return this.http.post<CalibratedTag>(`/api/tags/calibrate/${scanToken}`, position);
  }
}
