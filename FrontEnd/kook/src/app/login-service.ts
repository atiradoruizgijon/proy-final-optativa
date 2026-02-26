import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  private baseUrl = 'http://localhost:8080/auth/';

  constructor(private http:HttpClient) {}

  iniciarSesion(credenciales: {username: string, password: string}) {
    return this.http.post<any>(this.baseUrl + 'login', credenciales);
  }

  registro(credenciales: {username: string, email: string, password1: string, password2: string}) {
    return this.http.post<any>(`${this.baseUrl}register`, credenciales);
  }
}
