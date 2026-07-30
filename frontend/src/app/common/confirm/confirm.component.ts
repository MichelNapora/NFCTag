import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';

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

  /** Affiche ou masque la boîte. */
  @Input() open = false;

  @Input() title = 'Confirmer';
  @Input() message = '';

  /** Libellé du bouton de validation. */
  @Input() confirmLabel = 'Confirmer';

  /** true = bouton rouge, pour les actions destructrices. */
  @Input() danger = false;

  @Output() confirmed = new EventEmitter<void>();
  @Output() cancelled = new EventEmitter<void>();
}
