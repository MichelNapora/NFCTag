import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EmployeeService } from './employee.service';
import { Employee } from './employee.models';
import { AuthService } from '../../common/auth/auth.service';
import { errorMessage } from '../../common/http-error';

interface EmployeeForm {
  id: string | null;
  firstname: string;
  lastname: string;
  email: string;
  role: 'ADMIN' | 'EMPLOYEE';
  password: string;
  passwordConfirm: string;
}

const EMPTY_FORM: EmployeeForm = { id: null, firstname: '', lastname: '', email: '', role: 'EMPLOYEE', password: '', passwordConfirm: '' };

@Component({
  selector: 'app-employees',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './employees.component.html',
  styleUrl: './employees.component.scss'
})
export class EmployeesComponent implements OnInit {

  employees: Employee[] = [];
  loading = true;
  error: string | null = null;
  query = '';

  modalOpen = false;
  form: EmployeeForm = { ...EMPTY_FORM };
  saving = false;
  showPassword = false;
  showConfirm = false;

  constructor(
    private employeeService: EmployeeService,
    public auth: AuthService
  ) {}

  ngOnInit(): void {
    this.reload();
  }

  reload(): void {
    this.loading = true;
    this.employeeService.findAll().subscribe({
      next: (d) => { this.employees = d; this.loading = false; },
      error: (e) => this.fail(e)
    });
  }

  get filtered(): Employee[] {
    const q = this.query.trim().toLowerCase();
    return this.employees.filter(e =>
      !q ||
      e.firstname.toLowerCase().includes(q) ||
      e.lastname.toLowerCase().includes(q) ||
      e.email.toLowerCase().includes(q)
    );
  }

  isMe(e: Employee): boolean {
    return e.id === this.auth.employee?.id;
  }

  openCreate(): void {
    this.form = { ...EMPTY_FORM };
    this.showPassword = false;
    this.showConfirm = false;
    this.modalOpen = true;
  }

  openEdit(e: Employee): void {
    this.form = { id: e.id, firstname: e.firstname, lastname: e.lastname, email: e.email, role: e.role, password: '', passwordConfirm: '' };
    this.showPassword = false;
    this.showConfirm = false;
    this.modalOpen = true;
  }

  close(): void {
    this.modalOpen = false;
  }

  /** Les deux saisies du mot de passe ne correspondent pas. */
  get passwordMismatch(): boolean {
    return (this.form.password.length > 0 || this.form.passwordConfirm.length > 0)
        && this.form.password !== this.form.passwordConfirm;
  }

  get canSave(): boolean {
    const f = this.form;
    const base = !!(f.firstname.trim() && f.lastname.trim() && f.email.trim());
    if (this.passwordMismatch) { return false; }
    // à la création, le mot de passe initial est obligatoire
    return f.id ? base : base && f.password.length > 0;
  }

  save(): void {
    if (!this.canSave || this.saving) { return; }
    this.saving = true;
    const f = this.form;
    const call = f.id
      ? this.employeeService.update(f.id, f.firstname.trim(), f.lastname.trim(), f.email.trim(), f.role, f.password || null)
      : this.employeeService.create(f.firstname.trim(), f.lastname.trim(), f.email.trim(), f.role, f.password);

    call.subscribe({
      next: () => { this.saving = false; this.modalOpen = false; this.reload(); },
      error: (e) => { this.saving = false; this.fail(e); }
    });
  }

  remove(e: Employee): void {
    if (this.isMe(e)) { return; }
    if (!confirm(`Supprimer le compte de « ${e.firstname} ${e.lastname} » ?`)) { return; }
    this.employeeService.delete(e.id).subscribe({
      next: () => this.reload(),
      error: (err) => this.fail(err)
    });
  }

  private fail(e: any): void {
    this.loading = false;
    this.error = errorMessage(e, 'Une erreur est survenue.');
    setTimeout(() => this.error = null, 5000);
  }
}
