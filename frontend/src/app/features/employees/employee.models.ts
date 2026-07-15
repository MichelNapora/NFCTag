/** EmployeeDTO du back (le mot de passe n'en sort jamais). */
export interface Employee {
  id: string;
  firstname: string;
  lastname: string;
  email: string;
  role: 'ADMIN' | 'EMPLOYEE';
}
