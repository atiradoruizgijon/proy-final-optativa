import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class ChatService {

  private baseUrl = 'http://localhost:8080/chat';

  constructor(private http: HttpClient) {}

  askForRecipe(mensaje: string) {
    return this.http.post<any>(`${this.baseUrl}/receta`, { mensaje });
  }

  chat(mensaje: string) {
    return this.http.post<any>(`${this.baseUrl}/general`, { mensaje });
  }
}
