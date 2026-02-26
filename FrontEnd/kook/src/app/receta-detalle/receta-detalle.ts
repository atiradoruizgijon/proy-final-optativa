import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RecetaService } from '../receta-service';
import { FormGroup, FormControl } from '@angular/forms';

@Component({
  selector: 'app-receta-detalle',
  standalone: false,
  templateUrl: './receta-detalle.html',
  styleUrl: './receta-detalle.css'
})
export class RecetaDetalle implements OnInit {
  receta: any = null;
  errorMsg = '';
  id: number = 0;
  isEditing = false;
  successMsg = '';

  formReceta = new FormGroup({
    titulo: new FormControl('', { nonNullable: true }),
    descripcion: new FormControl('', { nonNullable: true }),
    instrucciones: new FormControl('', { nonNullable: true })
  });

  constructor(
    private recetaService: RecetaService,
    private route: ActivatedRoute,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    const idStr = this.route.snapshot.paramMap.get('id');
    if (idStr) {
      this.id = parseInt(idStr, 10);
      this.cargarReceta();
    }
  }

  cargarReceta() {
    console.log('Cargando receta con ID:', this.id);
    this.recetaService.rescatarReceta(this.id).subscribe(
      (datos) => {
        console.log('Receta cargada exitosamente:', datos);
        this.receta = datos;
        this.cdr.detectChanges();
      },
      (error) => {
        console.error('Error al cargar receta - Full error:', error);
        console.error('Status:', error.status);
        console.error('Status Text:', error.statusText);
        console.error('Message:', error.message);
        console.error('Error body:', error.error);
        console.error('ResponseURL:', error.url);
        this.errorMsg = `No se pudo cargar la receta: ${error.status} ${error.statusText}`;
      }
    );
  }

  volver() {
    this.router.navigate(['/recetas']);
  }

  eliminarReceta() {
    if (confirm('¿Estás seguro de que deseas eliminar esta receta?')) {
      this.recetaService.eliminarReceta(this.id).subscribe(
        () => {
          this.router.navigate(['/recetas']);
        },
        (error) => {
          console.error('Error al eliminar receta:', error);
          this.errorMsg = 'Error al eliminar la receta';
        }
      );
    }
  }

  toggleEdit() {
    if (!this.isEditing && this.receta) {
      // Cargar datos actuales en el formulario
      this.formReceta.patchValue({
        titulo: this.receta.titulo,
        descripcion: this.receta.descripcion,
        instrucciones: this.receta.instrucciones
      });
    }
    this.isEditing = !this.isEditing;
    this.errorMsg = '';
    this.successMsg = '';
  }

  guardarCambios() {
    this.errorMsg = '';
    this.successMsg = '';

    if (!this.formReceta.value.titulo || !this.formReceta.value.descripcion ||
        !this.formReceta.value.instrucciones) {
      this.errorMsg = 'Por favor completa todos los campos';
      return;
    }

    const recetaActualizada = {
      titulo: this.formReceta.value.titulo,
      descripcion: this.formReceta.value.descripcion,
      instrucciones: this.formReceta.value.instrucciones,
      imagenUrl: this.receta.imagenUrl
    };

    this.recetaService.actualizarReceta(this.id, recetaActualizada).subscribe(
      (datosReceta) => {
        console.log('Receta actualizada:', datosReceta);
        this.receta = datosReceta;
        this.successMsg = 'Receta actualizada correctamente!';
        this.isEditing = false;
        this.cdr.detectChanges();

        // Limpiar mensaje después de 3 segundos
        setTimeout(() => {
          this.successMsg = '';
          this.cdr.detectChanges();
        }, 3000);
      },
      (error) => {
        console.error('Error al actualizar receta:', error);
        this.errorMsg = 'Error al actualizar la receta. Intenta nuevamente.';
      }
    );
  }

  cancelarEdicion() {
    this.isEditing = false;
    this.errorMsg = '';
    this.successMsg = '';
    this.formReceta.reset();
  }
}
