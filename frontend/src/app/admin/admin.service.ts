import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BuildingAdmin, BusinessAdmin, TagAdmin, WingAdmin } from './admin.models';

/** Appels API d'administration (zone protégée /api/admin). */
@Injectable({ providedIn: 'root' })
export class AdminService {

  private readonly base = '/api/admin';

  constructor(private http: HttpClient) {}

  // Sociétés
  businesses(): Observable<BusinessAdmin[]> {
    return this.http.get<BusinessAdmin[]>(`${this.base}/businesses`);
  }
  createBusiness(name: string, bce: string): Observable<BusinessAdmin> {
    return this.http.post<BusinessAdmin>(`${this.base}/businesses`, { name, bce });
  }
  deleteBusiness(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/businesses/${id}`);
  }

  // Bâtiments
  buildings(): Observable<BuildingAdmin[]> {
    return this.http.get<BuildingAdmin[]>(`${this.base}/buildings`);
  }
  createBuilding(body: Partial<BuildingAdmin> & { street?: string; number?: string; postalCode?: string }):
    Observable<BuildingAdmin> {
    return this.http.post<BuildingAdmin>(`${this.base}/buildings`, body);
  }
  deleteBuilding(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/buildings/${id}`);
  }

  // Ailes
  wings(): Observable<WingAdmin[]> {
    return this.http.get<WingAdmin[]>(`${this.base}/wings`);
  }
  createWing(buildingId: number, name: string): Observable<WingAdmin> {
    return this.http.post<WingAdmin>(`${this.base}/wings`, { buildingId, name });
  }
  deleteWing(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/wings/${id}`);
  }

  // Tags
  tags(): Observable<TagAdmin[]> {
    return this.http.get<TagAdmin[]>(`${this.base}/tags`);
  }
  createTag(wingId: number, name: string): Observable<TagAdmin> {
    return this.http.post<TagAdmin>(`${this.base}/tags`, { wingId, name });
  }
  deleteTag(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/tags/${id}`);
  }
}
