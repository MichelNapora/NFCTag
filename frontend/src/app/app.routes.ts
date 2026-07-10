import { Routes } from '@angular/router';
import { ScanComponent } from './features/scan/scan.component';
import { ShellComponent } from './common/shell/shell.component';
import { DashboardComponent } from './features/dashboard/dashboard.component';
import { InterventionsComponent } from './features/interventions/interventions.component';
import { MapComponent } from './features/map/map.component';
import { BuildingsComponent } from './features/buildings/buildings.component';
import { WingsComponent } from './features/wings/wings.component';
import { TagsComponent } from './features/tags/tags.component';
import { BusinessesComponent } from './features/businesses/businesses.component';

export const routes: Routes = [
  // URL inscrite sur le tag NFC : /scan/<scan_token> — publique, sans barre latérale
  { path: 'scan/:token', component: ScanComponent },

  // Back-office (collègues) — sera protégé par le SSO Windows
  {
    path: '',
    component: ShellComponent,
    children: [
      { path: 'dashboard', component: DashboardComponent },
      { path: 'interventions', component: InterventionsComponent },
      { path: 'carte', component: MapComponent },
      { path: 'batiments', component: BuildingsComponent },
      { path: 'ailes', component: WingsComponent },
      { path: 'tags', component: TagsComponent },
      { path: 'societes', component: BusinessesComponent },
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
    ]
  },

  { path: '**', redirectTo: 'dashboard' }
];
