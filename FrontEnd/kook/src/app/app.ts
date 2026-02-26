import { Component, signal } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-root',
  templateUrl: './app.html',
  standalone: false,
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('kook');
  isLoginOrRegister = false;
  isLoggedIn = false;
  username = '';

  constructor(private router: Router) {
    // comprobar que el usuario esta logueado
    this.checkLoginStatus();

    // Subscribe a los cambios de ruta para ocultar/poner header y footer
    this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe((event: any) => {
        const url = event.urlAfterRedirects || event.url;
        this.isLoginOrRegister = url.includes('login') || url.includes('register');
        this.checkLoginStatus();
      });
  }

  checkLoginStatus() {
    const token = sessionStorage.getItem('jwtToken');
    const username = sessionStorage.getItem('username');
    console.log('[App] Verificando estado de login:');
    console.log('[App] Token en sessionStorage:', token ? 'SI (primeros 20 chars: ' + token.substring(0, 20) + '...)' : 'NO');
    console.log('[App] Username en sessionStorage:', username || 'NO');
    this.isLoggedIn = !!token;
    this.username = username || '';
  }

  logout() {
    sessionStorage.removeItem('jwtToken');
    sessionStorage.removeItem('username');
    this.isLoggedIn = false;
    this.router.navigate(['/login']);
  }
}
