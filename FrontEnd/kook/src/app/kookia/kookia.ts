import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { ChatService } from '../chat-service';
import { RecetaService } from '../receta-service';
import { FormGroup, FormControl } from '@angular/forms';

interface Message {
  type: 'user' | 'bot';
  content: string;
  timestamp: Date;
}

@Component({
  selector: 'app-kookia',
  standalone: false,
  templateUrl: './kookia.html',
  styleUrl: './kookia.css',
})
export class Kookia implements OnInit {
  messages: Message[] = [];
  inputMessage = '';
  isLoading = false;
  errorMsg = '';
  successMsg = '';

  // Para guardar receta desde chat
  showSaveRecipeForm = false;
  selectedMessage = '';
  isUploadingImage = false;
  selectedImageName = '';
  formReceta = new FormGroup({
    titulo: new FormControl('', { nonNullable: true }),
    descripcion: new FormControl('', { nonNullable: true }),
    instrucciones: new FormControl('', { nonNullable: true }),
    imagenUrl: new FormControl('', { nonNullable: true })
  });

  constructor(
    private chatService: ChatService,
    private recetaService: RecetaService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    // Mensaje de bienvenida
    this.messages.push({
      type: 'bot',
      content: '¡Hola! Soy KookIA, tu asistente de cocina. Puedo ayudarte con recetas, preguntas sobre cocina y mucho más. ¿Qué te gustaría saber?',
      timestamp: new Date()
    });
  }

  sendMessage(): void {
    if (!this.inputMessage.trim()) return;

    // Agregar mensaje del usuario
    this.messages.push({
      type: 'user',
      content: this.inputMessage,
      timestamp: new Date()
    });

    const userMessage = this.inputMessage;
    this.inputMessage = '';
    this.isLoading = true;
    this.errorMsg = '';

    // Detectar si pregunta por receta
    const isPedidoReceta = userMessage.toLowerCase().includes('receta') ||
                           userMessage.toLowerCase().includes('cómo hacer') ||
                           userMessage.toLowerCase().includes('como hacer');

    const chatCall = isPedidoReceta
      ? this.chatService.askForRecipe(userMessage)
      : this.chatService.chat(userMessage);

    chatCall.subscribe(
      (response) => {
        console.log('Respuesta de ChatGPT:', response);
        this.messages.push({
          type: 'bot',
          content: response.respuesta,
          timestamp: new Date()
        });
        this.isLoading = false;
        this.cdr.detectChanges();
      },
      (error) => {
        console.error('Error al comunicarse con ChatGPT:', error);
        this.errorMsg = 'Error al procesar tu mensaje. Intenta nuevamente.';
        this.isLoading = false;
        this.messages.push({
          type: 'bot',
          content: 'Lo siento, tuve un problema al procesar tu pregunta. ¿Puedes intentar de nuevo?',
          timestamp: new Date()
        });
        this.cdr.detectChanges();
      }
    );
  }

  prepareRecipeForSave(messageIndex: number): void {
    const message = this.messages[messageIndex];
    this.selectedMessage = message.content;
    this.showSaveRecipeForm = true;

    // Extraer título, descripción e instrucciones de la respuesta
    const { titulo, descripcion, instrucciones } = this.parseRecipeFromResponse(message.content);

    this.formReceta.patchValue({
      titulo: titulo,
      descripcion: descripcion,
      instrucciones: instrucciones
    });
  }

  private parseRecipeFromResponse(content: string): { titulo: string; descripcion: string; instrucciones: string } {
    const lines = content.split('\n').map(line => line.trim()).filter(line => line.length > 0);

    let titulo = '';
    let descripcion = '';
    let instrucciones = '';

    if (lines.length === 0) return { titulo, descripcion, instrucciones };

    // Extraer título (primera línea, removiendo markdown headers)
    titulo = lines[0].replace(/^#+\s*/, '').replace(/\*\*/g, '');

    // Buscar índices de secciones clave
    const ingredientesIndex = lines.findIndex(line =>
      line.toLowerCase().includes('ingrediente') ||
      line.toLowerCase().includes('ingredients')
    );

    const instruccionesIndex = lines.findIndex((line, idx) =>
      idx > ingredientesIndex && (
        line.toLowerCase().includes('instrucción') ||
        line.toLowerCase().includes('pasos') ||
        line.toLowerCase().includes('instructions') ||
        line.toLowerCase().includes('steps') ||
        line.toLowerCase().includes('preparación') ||
        line.toLowerCase().includes('preparation')
      )
    );

    // Extraer descripción (entre título e ingredientes)
    if (ingredientesIndex > 1) {
      descripcion = lines.slice(1, ingredientesIndex).join(' ').trim();
    } else if (lines.length > 1) {
      descripcion = lines.slice(1, Math.min(3, lines.length)).join(' ').trim();
    }

    // Extraer instrucciones
    if (instruccionesIndex > 0) {
      instrucciones = lines.slice(instruccionesIndex + 1).join('\n').trim();
    } else if (ingredientesIndex > 0) {
      instrucciones = lines.slice(ingredientesIndex + 1).join('\n').trim();
    } else if (lines.length > 2) {
      instrucciones = lines.slice(2).join('\n').trim();
    }

    // Limpiar caracteres de markdown
    descripcion = descripcion.replace(/\*\*/g, '').replace(/#+\s*/g, '');
    instrucciones = instrucciones.replace(/\*\*/g, '').replace(/#+\s*/g, '');

    return { titulo, descripcion, instrucciones };
  }

  cancelSaveRecipe(): void {
    this.showSaveRecipeForm = false;
    this.selectedMessage = '';
    this.formReceta.reset();
    this.errorMsg = '';
    this.successMsg = '';
    this.selectedImageName = '';
  }

  onFileSelected(event: any): void {
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

  saveRecipeFromChat(): void {
    this.errorMsg = '';
    this.successMsg = '';

    if (!this.formReceta.value.titulo || !this.formReceta.value.descripcion ||
        !this.formReceta.value.instrucciones) {
      this.errorMsg = 'Por favor completa todos los campos obligatorios';
      return;
    }

    // Si no hay URL de imagen, usar una por defecto o vacía
    const imagenUrl = this.formReceta.value.imagenUrl || 'https://via.placeholder.com/300x200?text=Receta';

    const nuevaReceta = {
      titulo: this.formReceta.value.titulo,
      descripcion: this.formReceta.value.descripcion,
      instrucciones: this.formReceta.value.instrucciones,
      imagenUrl: imagenUrl
    };

    this.recetaService.crearReceta(nuevaReceta).subscribe(
      (response) => {
        console.log('Receta guardada desde chat:', response);
        this.successMsg = '¡Receta guardada correctamente!';
        this.formReceta.reset();
        this.showSaveRecipeForm = false;
        this.selectedMessage = '';

        // Agregar mensaje de confirmación del bot
        this.messages.push({
          type: 'bot',
          content: `¡Perfecto! He guardado "${this.formReceta.value.titulo}" en tu colección de recetas. ¿Hay algo más que quieras saber?`,
          timestamp: new Date()
        });

        setTimeout(() => {
          this.successMsg = '';
          this.cdr.detectChanges();
        }, 3000);

        this.cdr.detectChanges();
      },
      (error) => {
        console.error('Error al guardar receta:', error);
        this.errorMsg = 'Error al guardar la receta. Intenta nuevamente.';
      }
    );
  }
}
