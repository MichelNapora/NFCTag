import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Technician } from './technician.models';

@Injectable({ providedIn: 'root' })
export class TechnicianService {

  constructor(private http: HttpClient) {}

  findAll(): Observable<Technician[]> {
    return this.http.get<Technician[]>('/api/technicians');
  }

  update(id: string, firstname: string, lastname: string, mobile: string, businessId: string): Observable<Technician> {
    return this.http.put<Technician>(`/api/technicians/${id}`, { firstname, lastname, mobile, businessId });
  }

  delete(id: string): Observable<void> {
    return this.http.delete<void>(`/api/technicians/${id}`);
  }
}
