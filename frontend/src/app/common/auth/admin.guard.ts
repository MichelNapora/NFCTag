import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { map } from 'rxjs/operators';
import { AuthService } from './auth.service';

/** Réservé aux ADMIN (ex. la page Utilisateurs) : sinon retour au tableau de bord. */
export const adminGuard: CanActivateFn = () => {
  const auth = inject(AuthService);
  const router = inject(Router);

  return auth.isLoggedIn().pipe(
    map(loggedIn => (loggedIn && auth.isAdmin) ? true : router.parseUrl('/dashboard'))
  );
};
