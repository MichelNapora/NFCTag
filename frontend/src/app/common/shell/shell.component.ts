import { Component, OnInit, inject, DestroyRef } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { CommonModule } from '@angular/common';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { Counts, CountsService } from './counts.service';
import { AuthService } from '../auth/auth.service';
import {ConfirmComponent} from '../confirm/confirm.component';
import {ConfirmService} from '../confirm/confirm.service';
import {catchError} from 'rxjs/operators';
import { interval, of } from 'rxjs';
import { CONFIRM_LOGOUT, BUTTON_LOGOUT, TITLE_LOGOUT, MSG } from '../messages';


/** Les interventions arrivent des scans, sans action de l'utilisateur : on relit les compteurs. */
const COUNTS_REFRESH_MS = 60_000;


@Component({
  selector: 'app-shell',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink, RouterLinkActive,ConfirmComponent],
  templateUrl: './shell.component.html',
  styleUrl: './shell.component.scss'
})
export class ShellComponent implements OnInit {
  private destroyRef = inject(DestroyRef);
  counts: Counts = { presences: 0, buildings: 0, wings: 0, tags: 0, businesses: 0, technicians:0 };

  readonly msg = MSG;

  constructor(
    private countsService: CountsService,
    public auth: AuthService,
    public confirm: ConfirmService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.countsService.counts$
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(c => this.counts = c);
    this.countsService.refresh();

    interval(COUNTS_REFRESH_MS)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(() => this.countsService.refresh());
  }

  /** Demande confirmation, puis déconnecte. */
  logout(): void {
    this.confirm.ask({
      title: TITLE_LOGOUT,
      message: CONFIRM_LOGOUT,
      confirmLabel: BUTTON_LOGOUT,
      // Même si le serveur ne répond pas, on veut sortir : l'échec est absorbé ici.
      action: () => this.auth.logout().pipe(catchError(() => of(null)))
          }).subscribe(() => { this.auth.clear(); this.router.navigate(['/login']); });
     }
   }
