import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Wing } from './wing.models';

@Injectable({ providedIn: 'root' })
export class WingService {

  constructor(private http: HttpClient) {}

  findAll(): Observable<Wing[]> {
    return this.http.get<Wing[]>('/api/wings');
  }

  create(name: string, buildingId: string): Observable<Wing> {
    return this.http.post<Wing>('/api/wings', { name, buildingId });
  }

  update(id: string, name: string, buildingId: string): Observable<Wing> {
    return this.http.put<Wing>(`/api/wings/${id}`, { name, buildingId });
  }

  delete(id: string): Observable<void> {
    return this.http.delete<void>(`/api/wings/${id}`);
  }

  idsInUse(): Observable<string[]> {
    return this.http.get<string[]>('/api/wings/ids-in-use');
  }
}
