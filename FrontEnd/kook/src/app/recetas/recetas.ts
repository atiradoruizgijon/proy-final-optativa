import { Component } from '@angular/core';

@Component({
  selector: 'app-recetas',
  standalone: false,
  templateUrl: './recetas.html',
  styleUrl: './recetas.css',
})
export class Recetas {
  recetas = [
    {
      titulo: "Espaguetis a la carbonara",
      descripcion: "Lorem ipsum dolor sit amet consectetur adipisicing elit. Sunt adipisci quae quas? NSunt adipisci quae quas? Nam, id. Corporis necessitatibus earum vero possimus adSunt adipisci quae quas? Nam, id. Corporis necessitatibus earum vero possimus adSunt adipisci quae quas? Nam, id. Corporis necessitatibus earum vero possimus adam, id. Corporis necessitatibus earum vero possimus ad."
    },
    {
      titulo: "Espaguetis con tomate",
      descripcion: "Lorem ipsum dolor sit ameSunt adipisci quae quas? Nam, id. Corporis necessitatibus earum vero possimus adSunt adipisci quae quas? Nam, id. Corporis necessitatibus earum vero possimus adt consectetur adipisicing elit. Sunt adipisci quae quas? Nam, id. Corporis necessitatibus earum vero possimus ad."
    },
    {
      titulo: "Espaguetis a la carbonara",
      descripcion: "Lorem ipsum dolor sit amet consectetur adipisicing elit. Sunt adipisci quae quas? Nam, id. Corporis necessitatibus earum vero possimus ad."
    },
    {
      titulo: "Espaguetis con tomate",
      descripcion: "Lorem ipsum dolor sit amet consectetur adipisicing elit. Sunt adipisci quae quas? Nam, id. Corporis necessitatibus earum vero possimus ad."
    },
    {
      titulo: "Espaguetis a la carbonara",
      descripcion: "Lorem ipsum dolor sit amet consectetur adipisicing elit. Sunt adipisci quae quas? Nam, id. Corporis necessitatibus earum vero possimus ad.Sunt adipisci quae quas? Nam, id. Corporis necessitatibus earum vero possimus adSunt adipisci quae quas? Nam, id. Corporis necessitatibus earum vero possimus ad"
    },
    {
      titulo: "Espaguetis con tomate",
      descripcion: "Lorem ipsum dolor sit amet consectetur adipisicing elit. Sunt adipisci quae quas? Nam, id. Corporis necessitatibus earum vero possimus ad."
    },
    {
      titulo: "Espaguetis a la carbonara",
      descripcion: "Lorem ipsum dolor sit amet consectetur adipisicing elit. Sunt adipisci quae quas? Nam, id. Corporis necessitatibus earum vero possimus ad."
    }
  ];
}
