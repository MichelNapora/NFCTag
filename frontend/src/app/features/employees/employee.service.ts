import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Employee } from './employee.models';

@Injectable({ providedIn: 'root' })
export class EmployeeService {

  constructor(private http: HttpClient) {}

  findAll(): Observable<Employee[]> {
    return this.http.get<Employee[]>('/api/employees');
  }

  create(firstname: string, lastname: string, email: string, role: string, password: string): Observable<Employee> {
    return this.http.post<Employee>('/api/employees', { firstname, lastname, email, role, password });
  }

  /** password vide → le back garde l'ancien mot de passe. */
  update(id: string, firstname: string, lastname: string, email: string, role: string, password: string | null): Observable<Employee> {
    return this.http.put<Employee>(`/api/employees/${id}`, { firstname, lastname, email, role, password });
  }

  delete(id: string): Observable<void> {
    return this.http.delete<void>(`/api/employees/${id}`);
  }
}
