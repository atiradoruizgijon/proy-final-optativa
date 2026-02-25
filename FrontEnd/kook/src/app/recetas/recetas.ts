import { Component, OnInit } from '@angular/core';
import { RecetaService } from '../receta-service';
import { recetaInterface } from '../receta-interface';

@Component({
  selector: 'app-recetas',
  standalone: false,
  templateUrl: './recetas.html',
  styleUrl: './recetas.css',
})
export class Recetas implements OnInit {
  recetas: any = [];

  constructor(private recetaService: RecetaService) {}
  ngOnInit(): void {
    this.recetas = this.recetaService.rescatarRecetas();
  }
}
