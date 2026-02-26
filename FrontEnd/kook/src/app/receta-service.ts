import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { timeout } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class RecetaService {

  private baseUrl = 'http://localhost:8080/recetas';
  private imagenUrl = 'http://localhost:8080/subir-imagen';

  constructor(private http:HttpClient) {}

  rescatarRecetas() {
    return this.http.get<any>(this.baseUrl);
  }

  rescatarReceta(id: number) {
    return this.http.get<any>(`${this.baseUrl}/${id}`);
  }

  crearReceta(receta: any) {
    return this.http.post<any>(`${this.baseUrl}/crear-receta`, receta);
  }

  subirImagen(file: File) {
    const formData = new FormData();
    formData.append('file', file);
    formData.append('name', file.name);
    // Add 30 second timeout to prevent infinite waiting
    return this.http.post<any>(this.imagenUrl, formData).pipe(
      timeout(30000)
    );
  }

  eliminarReceta(id: number) {
    return this.http.delete(`${this.baseUrl}/${id}`);
  }

  actualizarReceta(id: number, receta: any) {
    return this.http.put<any>(`${this.baseUrl}/${id}`, receta);
  }

}
