/** L'employé connecté (EmployeeDTO du back — jamais de mot de passe). */
export interface CurrentEmployee {
  id: string;
  firstname: string;
  lastname: string;
  email: string;
  role: 'ADMIN' | 'EMPLOYEE';
}
