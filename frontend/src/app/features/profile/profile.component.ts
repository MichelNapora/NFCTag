import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../common/auth/auth.service';
import { errorMessage } from '../../common/utils/http-error';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss'
})
export class ProfileComponent {

  currentPassword = '';
  newPassword = '';
  confirmPassword = '';

  saving = false;
  error: string | null = null;
  success = false;

  constructor(public auth: AuthService) {}

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
    this.success = false;

    this.auth.changePassword(this.currentPassword, this.newPassword).subscribe({
      next: () => {
        this.saving = false;
        this.success = true;
        this.currentPassword = '';
        this.newPassword = '';
        this.confirmPassword = '';
      },
      error: (e) => {
        this.saving = false;
        this.error = errorMessage(e, 'Changement impossible.');
      }
    });
  }
}
