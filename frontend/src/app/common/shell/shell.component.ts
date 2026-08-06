import { Component, OnInit, inject, DestroyRef } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { CommonModule } from '@angular/common';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { Counts, CountsService } from './counts.service';
import { AuthService } from '../auth/auth.service';
import {ConfirmComponent} from '../confirm/confirm.component';
import {ConfirmService} from '../confirm/confirm.service';
import {catchError} from 'rxjs/operators';
import { of } from 'rxjs';


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
  }

  /** Demande confirmation, puis déconnecte. */
  logout(): void {
    this.confirm.ask({
      title: 'Se déconnecter',
      message: 'Voulez-vous continuer ?',
      confirmLabel: 'Se déconnecter',
      // Même si le serveur ne répond pas, on veut sortir : l'échec est absorbé ici.
      action: () => this.auth.logout().pipe(catchError(() => of(null)))
    }).subscribe(() => { this.auth.clear(); this.router.navigate(['/login']); });
