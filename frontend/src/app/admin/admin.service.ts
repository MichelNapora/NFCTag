import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AddressAdmin, BuildingAdmin, BusinessAdmin, TagAdmin, WingAdmin } from './admin.models';

/** Appels API d'administration (sera protégée par le SSO). */
@Injectable({ providedIn: 'root' })
export class AdminService {

  constructor(private http: HttpClient) {}

  // Sociétés
  businesses(): Observable<BusinessAdmin[]> {
    return this.http.get<BusinessAdmin[]>('/api/businesses');
  }
  createBusiness(name: string, bce: string): Observable<BusinessAdmin> {
    return this.http.post<BusinessAdmin>('/api/businesses', { name, bce });
  }
  deleteBusiness(id: string): Observable<void> {
    return this.http.delete<void>(`/api/businesses/${id}`);
  }

  // Bâtiments (avec leur adresse)
  buildings(): Observable<BuildingAdmin[]> {
    return this.http.get<BuildingAdmin[]>('/api/buildings');
  }
  createBuilding(name: string, projectCode: string, address: AddressAdmin): Observable<BuildingAdmin> {
    return this.http.post<BuildingAdmin>('/api/buildings', { name, projectCode, address });
  }
  deleteBuilding(id: string): Observable<void> {
    return this.http.delete<void>(`/api/buildings/${id}`);
  }

  // Ailes
  wings(): Observable<WingAdmin[]> {
    return this.http.get<WingAdmin[]>('/api/wings');
  }
  createWing(name: string, buildingId: string): Observable<WingAdmin> {
    return this.http.post<WingAdmin>('/api/wings', { name, buildingId });
  }
  deleteWing(id: string): Observable<void> {
    return this.http.delete<void>(`/api/wings/${id}`);
  }

  // Tags
  tags(): Observable<TagAdmin[]> {
    return this.http.get<TagAdmin[]>('/api/tags');
  }
  createTag(wingId: string, latitude: number | null, longitude: number | null): Observable<TagAdmin> {
    return this.http.post<TagAdmin>('/api/tags', { wingId, latitude, longitude });
  }
  deleteTag(id: string): Observable<void> {
    return this.http.delete<void>(`/api/tags/${id}`);
  }
}
