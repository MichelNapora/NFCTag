import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { errorMessage } from '../utils/http-error';

/** Ce que l'appelant veut afficher dans la boîte, et ce qu'il veut faire. */
export interface ConfirmRequest {
  title: string;
  message: string;
  confirmLabel?: string;
  danger?: boolean;
  /** Exécutée si l'utilisateur confirme. Un échec s'affiche dans la boîte, qui reste ouverte. */
  action: () => Observable<unknown>;
}

/**
 * Pilote la boîte de confirmation unique de l'application.
 * La boîte ne se ferme que si l'action a réussi ; l'observable retourné
 * n'émet qu'à ce moment-là. En cas d'échec, le message reste sous les yeux
 * de l'utilisateur, là où il vient de cliquer.
 */
@Injectable({ providedIn: 'root' })
export class ConfirmService {

  open = false;
  title = '';
  message = '';
  confirmLabel = 'Confirmer';
  danger = false;

  /** Pourquoi l'action a échoué, affiché dans la boîte. */
  error: string | null = null;

  /** L'action est en cours : on empêche un second clic. */
  running = false;

  private request: ConfirmRequest | null = null;
  private pending: Subject<void> | null = null;

  ask(request: ConfirmRequest): Observable<void> {
    this.request = request;
    this.title = request.title;
    this.message = request.message;
    this.confirmLabel = request.confirmLabel ?? 'Confirmer';
    this.danger = request.danger ?? false;
    this.error = null;
    this.running = false;
    this.open = true;
    this.pending = new Subject<void>();
    return this.pending.asObservable();
  }

  /** L'utilisateur a validé : on exécute l'action avant de fermer quoi que ce soit. */
  accept(): void {
    if (this.running || !this.request) { return; }
    this.running = true;
    this.error = null;

    this.request.action().subscribe({
      next: () => {
        const pending = this.pending;
        this.close();
        pending?.next();
        pending?.complete();
      },
      error: (e) => {
        this.running = false;
        this.error = errorMessage(e, 'Action impossible.');
      }
    });
  }

  /** L'utilisateur a annulé : on complète sans jamais émettre. */
  reject(): void {
    if (this.running) { return; }
    const pending = this.pending;
    this.close();
    pending?.complete();
  }

  private close(): void {
    this.open = false;
    this.error = null;
    this.running = false;
    this.request = null;
    this.pending = null;
  }
}
