import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { LoginService } from '../login-service';
import { email } from '@angular/forms/signals';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: false,
  templateUrl: './register.html',
  styleUrl: './register.css',
})
export class Register {
  errorMsg = '';
  formLogin = new FormGroup({
      username: new FormControl('', { nonNullable: true }),
      email: new FormControl('', { nonNullable: true }),
      password1: new FormControl('', { nonNullable: true }),
      password2: new FormControl('', {nonNullable: true})
    });

  constructor(private loginService: LoginService, private router: Router) {}

  registroUsuario() {
    this.errorMsg = ''; // limpiar errores previos
    if (this.formLogin.value.password1 !== this.formLogin.value.password2) {
      this.errorMsg = "Las contraseñas no coinciden.";
      return;
    }

    if (!this.formLogin.value.username || !this.formLogin.value.email ||
        !this.formLogin.value.password1 || !this.formLogin.value.password2) {
      this.errorMsg = "Por favor completa todos los campos.";
      return;
    }
      this.loginService.registro(
        {
          username: this.formLogin.value.username ?? '',
          email: this.formLogin.value.email ?? '',
          password1: this.formLogin.value.password1 ?? '',
          password2: this.formLogin.value.password2 ?? ''
        }
      ).subscribe(
        (datos) => {
          console.log(datos);
          sessionStorage.setItem("username", datos.username);
          sessionStorage.setItem("jwtToken", datos.token);
          this.router.navigate(['/inicio']);
        },
        (error) => {
          console.error('Registration failed:', error);
          console.error('Status:', error.status);
          console.error('StatusText:', error.statusText);

          if (error.status === 400 || error.status === 409) {
            this.errorMsg = 'El usuario o email ya está en uso. Por favor elige otro.';
          } else if (error.status === 500) {
            this.errorMsg = 'Error del servidor. Por favor intenta más tarde.';
          } else if (error.status === 0 || !error.status) {
            this.errorMsg = 'No se puede conectar al servidor. Verifica tu conexión de internet.';
          } else {
            this.errorMsg = `Error en el registro: ${error.statusText || 'Error desconocido'}`;
          }
        }
      );
  }
}
