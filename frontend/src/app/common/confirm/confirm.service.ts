import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';

/** Ce que l'appelant veut afficher dans la boîte. */
export interface ConfirmRequest {
  title: string;
  message: string;
  confirmLabel?: string;
  danger?: boolean;
}

/**
 * Pilote la boîte de confirmation unique de l'application.
 * L'observable retourné n'émet que si l'utilisateur confirme.
 */
@Injectable({ providedIn: 'root' })
export class ConfirmService {

  open = false;
  title = '';
  message = '';
  confirmLabel = 'Confirmer';
  danger = false;

  private pending: Subject<void> | null = null;

  ask(request: ConfirmRequest): Observable<void> {
    this.title = request.title;
    this.message = request.message;
    this.confirmLabel = request.confirmLabel ?? 'Confirmer';
    this.danger = request.danger ?? false;
    this.open = true;
    this.pending = new Subject<void>();
    return this.pending.asObservable();
  }

  /** L'utilisateur a validé. */
  accept(): void {
    const pending = this.pending;
    this.close();
    pending?.next();
    pending?.complete();
  }

  /** L'utilisateur a annulé : on complète sans jamais émettre. */
  reject(): void {
    const pending = this.pending;
    this.close();
    pending?.complete();
  }

  private close(): void {
    this.open = false;
    this.pending = null;
  }
}
