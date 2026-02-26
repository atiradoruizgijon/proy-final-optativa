import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // No añadir el token en los endpoints de autorizacion
    // ya que son los que nos van a dar los tokens y ademas no necesitamos permisos
    if (req.url.includes('/auth/')) {
      console.log('[JwtInterceptor] Skipping token for auth endpoint:', req.url);
      return next.handle(req);
    }

    const token = sessionStorage.getItem('jwtToken');
    console.log('[JwtInterceptor] Verificando token para:', req.url);
    console.log('[JwtInterceptor] Token disponible:', token ? 'SI' : 'NO');

    // Clonar la peticion y añadir la autorizacion al header si el token existe
    if (token) {
      console.log('[JwtInterceptor] Añadiendo header Authorization');
      req = req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
    } else {
      console.log('[JwtInterceptor] Sin token, enviando petición sin autorización');
    }

    return next.handle(req);
  }
}
