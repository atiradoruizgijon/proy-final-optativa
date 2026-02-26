import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class ImagenService {
  private baseUrl = 'http://localhost:8080/imagen';

  constructor(private http:HttpClient) {}

  rescatarImagenPorId(id: number) {
    return this.http.get<any>(this.baseUrl + '?id=' + id);
  }

  rescatarImagenesPorUsuario(id: number) {
    return this.http.get<any>(`${this.baseUrl}-por-usuario?id=${id}`);
  }
}
