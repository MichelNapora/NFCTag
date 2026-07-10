import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Address, Building } from './building.models';

@Injectable({ providedIn: 'root' })
export class BuildingService {

  constructor(private http: HttpClient) {}

  findAll(): Observable<Building[]> {
    return this.http.get<Building[]>('/api/buildings');
  }

  create(name: string, projectCode: string, address: Address): Observable<Building> {
    return this.http.post<Building>('/api/buildings', { name, projectCode, address });
  }

  update(id: string, name: string, projectCode: string, address: Address): Observable<Building> {
    return this.http.put<Building>(`/api/buildings/${id}`, { name, projectCode, address });
  }

  delete(id: string): Observable<void> {
    return this.http.delete<void>(`/api/buildings/${id}`);
  }
}
