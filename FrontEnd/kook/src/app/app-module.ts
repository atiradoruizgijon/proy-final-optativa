import { NgModule, provideBrowserGlobalErrorListeners } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing-module';
import { App } from './app';
import { Kookia } from './kookia/kookia';
import { Recetas } from './recetas/recetas';
import { Planificacion } from './planificacion/planificacion';
import { Inicio } from './inicio/inicio';

@NgModule({
  declarations: [
    App,
    Kookia,
    Recetas,
    Planificacion,
    Inicio
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [
    provideBrowserGlobalErrorListeners(),
  ],
  bootstrap: [App]
})
export class AppModule { }
