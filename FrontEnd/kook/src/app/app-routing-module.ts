import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Recetas } from './recetas/recetas';
import { Planificacion } from './planificacion/planificacion';
import { Inicio } from './inicio/inicio';
import { Kookia } from './kookia/kookia';

const routes: Routes = [
  {
    path: 'kookia',
    component: Kookia
  },
  {
    path: 'recetas',
    component: Recetas
  },
  {
    path: 'planificacion',
    component: Planificacion,
  },
  {
    path: 'inicio',
    component: Inicio
  },
  {
    path: '',
    redirectTo: '/inicio',
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
