import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
  constructor(private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean {
    // comprobar si existe el token en sessionStorage
    const token = sessionStorage.getItem('jwtToken');

    if (token) {
      // el usuario esta logeado, permitir acceso
      return true;
    } else {
      // no esta logeado, redirijir a login
      this.router.navigate(['/login']);
      return false;
    }
  }
}
