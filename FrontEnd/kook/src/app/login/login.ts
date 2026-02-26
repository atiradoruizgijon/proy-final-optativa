import { Component, OnInit } from '@angular/core';
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

  constructor(private fb: FormBuilder, private loginService: LoginService, private router: Router) {}

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
        console.error('Login failed:', error);
        this.errorMsg = 'Error de login: Credenciales inválidas o servidor no disponible';
      }
    );
  }

  ngOnInit(): void {
    // this.loginForm = this.fb.group({
    //   username: ['', Validators.required],
    //   password: ['', Validators.required]
    // });
    // this.loginService.iniciarSesion(this.form.value);
  }

}
