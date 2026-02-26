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
    console.log("pasa");
    this.errorMsg = ''; // limpiar errores previos
    if (this.formLogin.value.password1 == this.formLogin.value.password2) {
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
          this.errorMsg = 'Error en el registro: Verifica que el usuario o email no estén en uso';
        }
      );
    } else {
      this.errorMsg = "Las contraseñas no coinciden.";
    }
  }
}
