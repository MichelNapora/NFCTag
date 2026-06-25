import { Routes } from '@angular/router';
import { ScanComponent } from './scan/scan.component';
import { DashboardComponent } from './backoffice/dashboard.component';

export const routes: Routes = [
  // URL inscrite sur le tag NFC : /scan/<scan_token>
  { path: 'scan/:token', component: ScanComponent },
  // Back-office (collègues)
  { path: 'dashboard', component: DashboardComponent },
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
  { path: '**', redirectTo: 'dashboard' }
];
