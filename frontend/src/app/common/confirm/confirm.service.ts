import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { errorMessage } from '../utils/http-error';
import {ACTION_FAILED, BUTTON_CONFIRM, DELETE_BLOCKED} from '../messages';


export interface ConfirmRequest {
  title: string;
  message: string;
  confirmLabel?: string;
  danger?: boolean;
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
  danger = false;
  confirmLabel = BUTTON_CONFIRM;
  error: string | null = null;
  running = false;

  private request: ConfirmRequest | null = null;
  private pending: Subject<void> | null = null;

  ask(request: ConfirmRequest): Observable<void> {
    this.request = request;
    this.title = request.title;
    this.message = request.message;
    this.confirmLabel = request.confirmLabel ?? BUTTON_CONFIRM;
    this.danger = request.danger ?? false;
    this.error = null;
    this.running = false;
    this.open = true;
    this.pending = new Subject<void>();
    return this.pending.asObservable();
  }

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
        this.error = errorMessage(e, ACTION_FAILED, DELETE_BLOCKED)
      }
    });
  }

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
