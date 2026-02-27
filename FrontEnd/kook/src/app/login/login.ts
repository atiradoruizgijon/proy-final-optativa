import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { LoginService } from '../login-service';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login implements OnInit {
  // loginForm!: FormGroup;
  errorMsg: string = '';

  formLogin = new FormGroup({
      username: new FormControl('', { nonNullable: true }),
      password: new FormControl('', { nonNullable: true })
    });

  constructor(private fb: FormBuilder, private loginService: LoginService, private router: Router, private cdr: ChangeDetectorRef) {}

  submit() {
    this.errorMsg = ''; // limpiar mensajes de error previos
    this.loginService.iniciarSesion(
      {username: this.formLogin.value.username ?? '', password: this.formLogin.value.password ?? ''}
    ).subscribe(
      (datos) => {
        console.log(datos);
        sessionStorage.setItem("username", datos.username);
        sessionStorage.setItem("jwtToken", datos.token);
        this.router.navigate(['/inicio']);
      },
      (error) => {
        console.error('Login failed - Full error object:', error);
        console.error('Status:', error.status);
        console.error('StatusText:', error.statusText);
        console.error('Error body:', error.error);
        console.error('Error message:', error.message);

        if (error.status === 401) {
          this.errorMsg = 'Credenciales inválidas. Por favor verifica tu usuario y contraseña.';
        } else if (error.status === 400) {
          this.errorMsg = typeof error.error === 'string' ? error.error : 'Solicitud inválida.';
        } else if (error.status === 409) {
          this.errorMsg = typeof error.error === 'string' ? error.error : 'Error de conflicto.';
        } else if (error.status === 500) {
          this.errorMsg = 'Error del servidor. Por favor intenta más tarde.';
        } else if (error.status === 0 || !error.status) {
          this.errorMsg = 'No se puede conectar al servidor. Verifica tu conexión de internet.';
        } else {
          this.errorMsg = typeof error.error === 'string' ? error.error : `Error de login: ${error.statusText || 'Error desconocido'}`;
        }
      }
    );
    this.cdr.detectChanges();
  }

  ngOnInit(): void {
    // this.loginForm = this.fb.group({
    //   username: ['', Validators.required],
    //   password: ['', Validators.required]
    // });
    // this.loginService.iniciarSesion(this.form.value);
  }

}
