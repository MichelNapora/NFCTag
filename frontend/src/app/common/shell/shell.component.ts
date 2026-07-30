import { Component, OnInit, inject, DestroyRef } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { CommonModule } from '@angular/common';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { Counts, CountsService } from './counts.service';
import { AuthService } from '../auth/auth.service';
import {ConfirmComponent} from '../confirm/confirm.component';


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
  logoutOpen = false;

  constructor(
    private countsService: CountsService,
    public auth: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.countsService.counts$
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(c => this.counts = c);
    this.countsService.refresh();
  }

  /** Ouvre la demande de confirmation. */
  logout(): void {
    this.logoutOpen = true;
  }

  cancelLogout(): void {
    this.logoutOpen = false;
  }

  /** Déconnexion réellement confirmée. */
  confirmLogout(): void {
    this.logoutOpen = false;
    this.auth.logout().subscribe({
      next: () => this.router.navigate(['/login']),
      error: () => { this.auth.clear(); this.router.navigate(['/login']); }
    });
  }
}
