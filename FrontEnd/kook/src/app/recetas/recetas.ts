import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { RecetaService } from '../receta-service';
import { recetaInterface } from '../receta-interface';
import { FormGroup, FormControl } from '@angular/forms';

@Component({
  selector: 'app-recetas',
  standalone: false,
  templateUrl: './recetas.html',
  styleUrl: './recetas.css',
})
export class Recetas implements OnInit {
  recetas: any = [];
  showCreateForm = false;
  errorMsg = '';
  successMsg = '';
  isUploadingImage = false;
  selectedImageName = '';

  formReceta = new FormGroup({
    titulo: new FormControl('', { nonNullable: true }),
    descripcion: new FormControl('', { nonNullable: true }),
    instrucciones: new FormControl('', { nonNullable: true }),
    imagenUrl: new FormControl('', { nonNullable: true })
  });

  constructor(private recetaService: RecetaService, private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {
    this.cargarRecetas();
  }

  cargarRecetas() {
    console.log('Iniciando carga de recetas...');
    this.recetaService.rescatarRecetas().subscribe(
      datos => {
        console.log('Recetas cargadas exitosamente:', datos);
        this.recetas = datos;
        this.cdr.detectChanges();
      },
      error => {
        console.error('Error al cargar recetas - Full error:', error);
        console.error('Status:', error.status);
        console.error('Status Text:', error.statusText);
        console.error('Message:', error.message);
        console.error('Error body:', error.error);
        this.errorMsg = `Error al cargar las recetas: ${error.status} ${error.statusText}`;
      }
    );
  }

  toggleCreateForm() {
    this.showCreateForm = !this.showCreateForm;
    this.errorMsg = '';
    this.successMsg = '';
    this.selectedImageName = '';
    if (!this.showCreateForm) {
      this.formReceta.reset();
    }
  }

  onFileSelected(event: any) {
    const file: File = event.target.files[0];
    if (file) {
      this.selectedImageName = file.name;
      this.isUploadingImage = true;
      this.errorMsg = '';
      this.successMsg = '';

      this.recetaService.subirImagen(file).subscribe(
        (response) => {
          console.log('Respuesta completa de imagen:', response);
          console.log('Tipo de respuesta:', typeof response);
          console.log('Keys de respuesta:', Object.keys(response));

          // Manejo robusto de la respuesta
          let imageUrl = '';
          if (response && typeof response === 'object') {
            imageUrl = response.url || response['url'] || '';
          }

          console.log('URL de imagen extraída:', imageUrl);

          if (imageUrl) {
            this.formReceta.patchValue({
              imagenUrl: imageUrl
            });
            this.successMsg = 'Imagen cargada exitosamente';
            console.log('Imagen establecida en formulario');

            // Limpiar mensaje después de 3 segundos
            setTimeout(() => {
              this.successMsg = '';
            }, 3000);
          } else {
            this.errorMsg = 'Error: No se recibió URL válida de la imagen';
            console.error('No se encontró URL en la respuesta:', response);
          }

          this.isUploadingImage = false;
          this.cdr.detectChanges();
        },
        (error) => {
          console.error('Error al subir imagen - Detalles completos:', error);
          console.error('Status:', error.status);
          console.error('Error message:', error.message);
          console.error('Error body:', error.error);
          console.error('Error name:', error.name);

          let errorMessage = 'Error al cargar la imagen. Intenta nuevamente.';
          if (error.name === 'TimeoutError') {
            errorMessage = 'La carga de la imagen tardó demasiado. Intenta con un archivo más pequeño.';
          }

          this.errorMsg = errorMessage;
          this.isUploadingImage = false;
          this.selectedImageName = '';
          this.cdr.detectChanges();
        }
      );
    }
  }

  crearReceta() {
    this.errorMsg = '';
    this.successMsg = '';

    if (!this.formReceta.value.titulo || !this.formReceta.value.descripcion ||
        !this.formReceta.value.instrucciones || !this.formReceta.value.imagenUrl) {
      this.errorMsg = 'Por favor completa todos los campos';
      return;
    }

    const nuevaReceta = {
      titulo: this.formReceta.value.titulo,
      descripcion: this.formReceta.value.descripcion,
      instrucciones: this.formReceta.value.instrucciones,
      imagenUrl: this.formReceta.value.imagenUrl
    };

    this.recetaService.crearReceta(nuevaReceta).subscribe(
      (datosReceta) => {
        console.log('Receta creada:', datosReceta);
        this.successMsg = 'Receta creada correctamente!';
        this.formReceta.reset();
        this.showCreateForm = false;
        this.selectedImageName = '';

        // Recargar las recetas después de 1 segundo
        setTimeout(() => {
          this.cargarRecetas();
        }, 1000);
      },
      (error) => {
        console.error('Error al crear receta:', error);
        this.errorMsg = 'Error al crear la receta. Intenta nuevamente.';
      }
    );
  }

  eliminarReceta(id: number) {
    if (confirm('¿Estás seguro de que deseas eliminar esta receta?')) {
      this.recetaService.eliminarReceta(id).subscribe(
        () => {
          console.log('Receta eliminada');
          this.successMsg = 'Receta eliminada correctamente';
          this.cargarRecetas();
        },
        (error) => {
          console.error('Error al eliminar receta:', error);
          this.errorMsg = 'Error al eliminar la receta. Intenta nuevamente.';
        }
      );
    }
  }
}
