import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { CurrentEmployee } from './auth.models';

/**
 * Gère la session côté front. Le backend pose un cookie de session HttpOnly :
 * rien à stocker ici, on garde juste en mémoire « qui est connecté ».
 */
@Injectable({ providedIn: 'root' })
export class AuthService {

  private readonly employeeSubject = new BehaviorSubject<CurrentEmployee | null>(null);
  readonly employee$ = this.employeeSubject.asObservable();

  /** true dès qu'on a demandé au moins une fois au back qui est connecté. */
  private checked = false;

  constructor(private http: HttpClient) {}

  get employee(): CurrentEmployee | null {
    return this.employeeSubject.value;
  }

  get isAdmin(): boolean {
    return this.employee?.role === 'ADMIN';
  }

  login(email: string, password: string): Observable<CurrentEmployee> {
    return this.http.post<CurrentEmployee>('/api/auth/login', { email, password }).pipe(
      tap(e => { this.employeeSubject.next(e); this.checked = true; })
    );
  }

  logout(): Observable<void> {
    return this.http.post<void>('/api/auth/logout', {}).pipe(
      tap(() => this.clear())
    );
  }

  /** Vide l'état local (après logout ou session expirée). */
  clear(): void {
    this.employeeSubject.next(null);
    this.checked = true;
  }

  /**
   * Est-on connecté ? Au premier appel (rechargement de page), on demande
   * au back — le cookie de session, lui, a survécu au rechargement.
   */
  isLoggedIn(): Observable<boolean> {
    if (this.checked) {
      return of(this.employee !== null);
    }
    return this.http.get<CurrentEmployee>('/api/auth/me').pipe(
      tap(e => { this.employeeSubject.next(e); this.checked = true; }),
      map(() => true),
      catchError(() => { this.clear(); return of(false); })
    );
  }
}
