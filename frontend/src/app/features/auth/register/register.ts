import { Component, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../../core/services/auth-service';
import { RegisterRequestDto } from '../../../core/models/register-request-dto';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, RouterLink],
  templateUrl: './register.html',
  styleUrl: './register.css'
})
export class Register {
  private authService = inject(AuthService);
  private router = inject(Router);

  registroForm: RegisterRequestDto = {
    name: '',
    email: '',
    password: ''
  };

  mostrarSenha = signal(false);

  mensagemErro = signal('');

  toggleMostrarSenha(): void {
    this.mostrarSenha.update(valor => !valor);
  }

  aoCadastrar(): void {
    this.mensagemErro.set('');

    this.authService.register(this.registroForm).subscribe({
      next: () => {
        this.router.navigate(['/login']);
      },
      error: () => {
        this.mensagemErro.set('Erro ao realizar o cadastro. Tente novamente.');
      }
    });
  }
}