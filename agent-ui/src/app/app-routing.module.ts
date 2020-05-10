import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { TestComponentComponent } from './test-component/test-component.component';


const routes: Routes = [
  { path: '', redirectTo: '/vehicles', pathMatch: 'full' },
  { path: 'rents', loadChildren: () => import('./modules/rents/rents.module').then(mod => mod.RentsModule)},
  { path: 'ads', loadChildren: () => import('./modules/ads/ads.module').then(mod => mod.AdsModule)},
  { path: 'vehicles', loadChildren: () => import('./modules/vehicles/vehicles.module').then(mod => mod.VehiclesModule)},
  { path: 'test', children: [
    { path: 'test', component: TestComponentComponent }
  ] },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
