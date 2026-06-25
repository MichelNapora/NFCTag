import { Routes } from '@angular/router';
import { ScanComponent } from './scan/scan.component';
import { DashboardComponent } from './backoffice/dashboard.component';
import { AdminComponent } from './admin/admin.component';

export const routes: Routes = [
  // URL inscrite sur le tag NFC : /scan/<scan_token>
  { path: 'scan/:token', component: ScanComponent },
  // Zone protégée (collègues) — futur SSO
  { path: 'dashboard', component: DashboardComponent },
  { path: 'admin', component: AdminComponent },
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
  { path: '**', redirectTo: 'dashboard' }
];
