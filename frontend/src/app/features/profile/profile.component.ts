import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../common/auth/auth.service';
import { errorMessage } from '../../common/utils/http-error';
import { PASSWORD_CHANGE_FAILED } from '../../common/messages';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss'
})
export class ProfileComponent {

  modalOpen = false;

  currentPassword = '';
  newPassword = '';
  confirmPassword = '';

  showCurrent = false;
  showNew = false;
  showConfirm = false;

  saving = false;
  error: string | null = null;
  success = false;

  constructor(public auth: AuthService) {}

  open(): void {
    this.reset();
    this.success = false;
    this.modalOpen = true;
  }

  close(): void {
    this.modalOpen = false;
    this.reset();
  }

  get canSave(): boolean {
    return !!(this.currentPassword && this.newPassword.length >= 8
      && this.newPassword === this.confirmPassword);
  }

  /** Message d'aide sous le formulaire, selon ce qui manque. */
  get hint(): string | null {
    if (this.newPassword && this.newPassword.length < 8) {
      return 'Le nouveau mot de passe doit faire au moins 8 caractères.';
    }
    if (this.confirmPassword && this.newPassword !== this.confirmPassword) {
      return 'Les deux mots de passe ne correspondent pas.';
    }
    return null;
  }

  save(): void {
    if (!this.canSave || this.saving) { return; }
    this.saving = true;
    this.error = null;

    this.auth.changePassword(this.currentPassword, this.newPassword).subscribe({
      next: () => {
        this.saving = false;
        this.success = true;
        this.close();
      },
      error: (e) => {
        this.saving = false;
        this.error = errorMessage(e, PASSWORD_CHANGE_FAILED)
      }
    });
  }

  /** Vide les champs et les yeux : rien ne doit survivre à la fermeture. */
  private reset(): void {
    this.currentPassword = '';
    this.newPassword = '';
    this.confirmPassword = '';
    this.showCurrent = false;
    this.showNew = false;
    this.showConfirm = false;
    this.error = null;
  }
}
