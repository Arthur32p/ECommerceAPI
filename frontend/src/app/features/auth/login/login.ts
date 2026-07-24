import { Component, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../../core/services/auth-service';
import { LoginRequestDto } from '../../../core/models/login-request-dto';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, RouterLink],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {
  private authService = inject(AuthService);
  private router = inject(Router);

  loginForm: LoginRequestDto = {
    email: '',
    password: ''
  };

  mostrarSenha = signal(false);

  mensagemErro = signal('');

  toggleMostrarSenha(): void {
    this.mostrarSenha.update(valor => !valor);
  }

  aoLogar(): void {
    this.mensagemErro.set('');

    this.authService.login(this.loginForm).subscribe({
      next: () => {
        this.router.navigate(['/dashboard']);
      },
      error: () => {
        this.mensagemErro.set('Email ou senha inválidos');
      }
    });
  }
}