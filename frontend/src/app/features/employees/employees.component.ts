import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EmployeeService } from './employee.service';
import { Employee } from './employee.models';
import { AuthService } from '../../common/auth/auth.service';
import { errorMessage } from '../../common/utils/http-error';
import {ConfirmService} from '../../common/confirm/confirm.service';
import { CONFIRM_UNLOCK_EMPLOYEE, CONFIRM_DELETE_EMPLOYEE, GENERIC_ERROR, BUTTON_DELETE, BUTTON_UNLOCK, TITLE_DELETE_EMPLOYEE, TITLE_UNLOCK_EMPLOYEE } from '../../common/messages';
import { format } from '../../common/utils/format';

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
  private errorTimer: ReturnType<typeof setTimeout> | null = null;
  query = '';

  modalOpen = false;
  form: EmployeeForm = { ...EMPTY_FORM };
  saving = false;
  showPassword = false;
  showConfirm = false;

  constructor(
    private employeeService: EmployeeService,
    public auth: AuthService,
    private confirm: ConfirmService
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

  unlock(e: Employee): void {
    this.confirm.ask({
      title: TITLE_UNLOCK_EMPLOYEE,
      message: format(CONFIRM_UNLOCK_EMPLOYEE, `${e.firstname} ${e.lastname}`),
      confirmLabel: BUTTON_UNLOCK,
      action: () => this.employeeService.unlock(e.id)
    }).subscribe(() => this.reload());
  }

  remove(e: Employee): void {
    if (this.isMe(e)) { return; }
    this.confirm.ask({
      title: TITLE_DELETE_EMPLOYEE,
      message: format(CONFIRM_DELETE_EMPLOYEE, `${e.firstname} ${e.lastname}`),
      confirmLabel: BUTTON_DELETE,
      danger: true,
      action: () => this.employeeService.delete(e.id)
    }).subscribe(() => this.reload());
  }

  private fail(e: any): void {
    this.loading = false;
    this.error =  errorMessage(e, GENERIC_ERROR)
    if (this.errorTimer) { clearTimeout(this.errorTimer); }
    this.errorTimer = setTimeout(() => this.error = null, 5000);
  }
}
