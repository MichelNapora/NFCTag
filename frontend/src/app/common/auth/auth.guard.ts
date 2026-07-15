import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { map } from 'rxjs/operators';
import { AuthService } from './auth.service';

/** Bloque les pages du back-office : pas connecté → page de connexion. */
export const authGuard: CanActivateFn = () => {
  const auth = inject(AuthService);
  const router = inject(Router);

  return auth.isLoggedIn().pipe(
    map(loggedIn => loggedIn ? true : router.parseUrl('/login'))
  );
};
