import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Business } from './business.models';

@Injectable({ providedIn: 'root' })
export class BusinessService {

  constructor(private http: HttpClient) {}

  findAll(): Observable<Business[]> {
    return this.http.get<Business[]>('/api/businesses');
  }

  create(name: string, bce: string): Observable<Business> {
    return this.http.post<Business>('/api/businesses', { name, bce });
  }

  update(id: string, name: string, bce: string): Observable<Business> {
    return this.http.put<Business>(`/api/businesses/${id}`, { name, bce });
  }

  delete(id: string): Observable<void> {
    return this.http.delete<void>(`/api/businesses/${id}`);
  }
}
