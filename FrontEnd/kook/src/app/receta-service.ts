import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class RecetaService {
  constructor(private http:HttpClient) {}

  rescatarRecetas() {
    return this.http.get<any>('http://localhost:8080/recetas');
  }
}
