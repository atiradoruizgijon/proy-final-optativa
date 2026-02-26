import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Recetas } from './recetas/recetas';
import { RecetaDetalle } from './receta-detalle/receta-detalle';
import { Inicio } from './inicio/inicio';
import { Kookia } from './kookia/kookia';
import { Login } from './login/login';
import { Register } from './register/register';
import { AuthGuard } from './auth-guard';

const routes: Routes = [
  {
    path: 'kookia',
    component: Kookia,
    canActivate: [AuthGuard]
  },
  {
    path: 'recetas',
    component: Recetas,
    canActivate: [AuthGuard]
  },
  {
    path: 'receta/:id',
    component: RecetaDetalle,
    canActivate: [AuthGuard]
  },
  {
    path: 'inicio',
    component: Inicio,
    canActivate: [AuthGuard]
  },
  {
    path: 'login',
    component: Login
  },
  {
    path: 'register',
    component: Register
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
