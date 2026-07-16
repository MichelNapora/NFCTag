import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../common/auth/auth.service';
import { errorMessage } from '../../common/http-error';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  email = '';
  password = '';
  showPassword = false;
  error: string | null = null;
  submitting = false;

  constructor(private auth: AuthService, private router: Router) {}

  get canSubmit(): boolean {
    return !!(this.email.trim() && this.password);
  }

  submit(): void {
    if (!this.canSubmit || this.submitting) { return; }
    this.submitting = true;
    this.error = null;
    this.auth.login(this.email.trim(), this.password).subscribe({
      next: () => this.router.navigate(['/dashboard']),
      error: (e) => {
        this.submitting = false;
        this.error = errorMessage(e, 'Connexion impossible. Réessayez.');
      }
    });
  }
}
