import { NgModule, provideBrowserGlobalErrorListeners } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing-module';
import { App } from './app';
import { Kookia } from './kookia/kookia';
import { Recetas } from './recetas/recetas';
import { RecetaDetalle } from './receta-detalle/receta-detalle';
import { Inicio } from './inicio/inicio';
import { Login } from './login/login';
import { Register } from './register/register';
import { JwtInterceptor } from './jwt-interceptor';

@NgModule({
  declarations: [
    App,
    Kookia,
    Recetas,
    RecetaDetalle,
    Inicio,
    Login,
    Register
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [
    provideBrowserGlobalErrorListeners(),
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true }
  ],
  bootstrap: [App]
})
export class AppModule { }
