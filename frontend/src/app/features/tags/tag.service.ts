import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Tag } from './tag.models';

@Injectable({ providedIn: 'root' })
export class TagService {

  constructor(private http: HttpClient) {}

  findAll(): Observable<Tag[]> {
    return this.http.get<Tag[]>('/api/tags');
  }

  create(wingId: string, latitude: number | null, longitude: number | null): Observable<Tag> {
    return this.http.post<Tag>('/api/tags', { wingId, latitude, longitude });
  }

  update(id: string, wingId: string, latitude: number | null, longitude: number | null): Observable<Tag> {
    return this.http.put<Tag>(`/api/tags/${id}`, { wingId, latitude, longitude });
  }

  delete(id: string): Observable<void> {
    return this.http.delete<void>(`/api/tags/${id}`);
  }
}
