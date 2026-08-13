import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import {BUTTON_CONFIRM, MSG} from '../messages';

/**
 * Boîte de confirmation réutilisable, aux couleurs de l'application.
 * Remplace le confirm() natif du navigateur.
 */
@Component({
  selector: 'app-confirm',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './confirm.component.html'
})
export class ConfirmComponent {


  readonly msg = MSG;
  /** Affiche ou masque la boîte. */
  @Input() open = false;

  @Input() title = BUTTON_CONFIRM;
  @Input() message = '';

  /** Libellé du bouton de validation. */
  @Input() confirmLabel = BUTTON_CONFIRM;

  /** true = bouton rouge, pour les actions destructrices. */
  @Input() danger = false;

  /** Pourquoi l'action a échoué. La boîte reste ouverte tant qu'il est là. */
  @Input() error: string | null = null;

  /** L'action est en cours : on grise le bouton de validation. */
  @Input() running = false;

  @Output() confirmed = new EventEmitter<void>();
  @Output() cancelled = new EventEmitter<void>();
}



