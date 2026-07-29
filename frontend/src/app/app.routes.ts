import { Routes } from '@angular/router';
import { ScanComponent } from './features/scan/scan.component';
import { LoginComponent } from './features/login/login.component';
import { ShellComponent } from './common/shell/shell.component';
import { DashboardComponent } from './features/dashboard/dashboard.component';
import { InterventionsComponent } from './features/interventions/interventions.component';
import { MapComponent } from './features/map/map.component';
import { BuildingsComponent } from './features/buildings/buildings.component';
import { WingsComponent } from './features/wings/wings.component';
import { TagsComponent } from './features/tags/tags.component';
import { BusinessesComponent } from './features/businesses/businesses.component';
import { EmployeesComponent } from './features/employees/employees.component';
import { ProfileComponent } from './features/profile/profile.component';
import { authGuard } from './common/auth/auth.guard';
import { adminGuard } from './common/auth/admin.guard';
import { TechniciansComponent } from './features/technicians/technicians.component';

export const routes: Routes = [
  { path: 'scan/:token', component: ScanComponent },

  // Page de connexion — publique
  { path: 'login', component: LoginComponent },

  // Back-office (employés Spi) — protégé par la connexion
  {
    path: '',
    component: ShellComponent,
    canActivate: [authGuard],
    canActivateChild: [authGuard],
    children: [
      { path: 'dashboard', component: DashboardComponent },
      { path: 'interventions', component: InterventionsComponent },
      { path: 'carte', component: MapComponent },
      { path: 'batiments', component: BuildingsComponent },
      { path: 'ailes', component: WingsComponent },
      { path: 'tags', component: TagsComponent },
      { path: 'societes', component: BusinessesComponent },
      { path: 'techniciens', component: TechniciansComponent },
      { path: 'profil', component: ProfileComponent },
      // Réservé aux administrateurs
      { path: 'utilisateurs', component: EmployeesComponent, canActivate: [adminGuard] },
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
    ]
  },

  { path: '**', redirectTo: 'dashboard' }
];
