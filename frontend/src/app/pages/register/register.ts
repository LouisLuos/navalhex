import { Component, inject, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { RegisterDTO, UserRole } from '../../core/models/user.model';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './register.html',
  styleUrl: './register.css',
})
export class Register {
  private authService = inject(AuthService);
  private router = inject(Router);

  isLoading = signal(false);
  errorMessage = signal<string | null>(null);

  registerForm = new FormGroup({
    name: new FormControl('', { nonNullable: true, validators: [Validators.required, Validators.minLength(3)] }),
    email: new FormControl('', { nonNullable: true, validators: [Validators.required, Validators.email] }),
    password: new FormControl('', { nonNullable: true, validators: [Validators.required, Validators.minLength(8)] }),
    confirmPassword: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
    whatsapp: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
    role: new FormControl<UserRole>('CUSTOMER', { nonNullable: true, validators: [Validators.required] }),
  });

  onSubmit() {
    if (this.registerForm.invalid) return;

    if (this.registerForm.value.password !== this.registerForm.value.confirmPassword) {
      this.errorMessage.set('As senhas não conferem!');
      return;
    }

    this.isLoading.set(true);
    this.errorMessage.set(null);

    this.authService.register(this.registerForm.value as RegisterDTO).subscribe({
      next: (response) => {
        this.isLoading.set(false);
        console.log('Sucesso:', response);
        // Redireciona para o login ou página principal
        this.router.navigate(['/login']);
      },
      error: (err) => {
        this.isLoading.set(false);
        console.error('Erro na requisição:', err);
        // Pega a mensagem de erro que nosso GlobalExceptionHandler devolveu
        this.errorMessage.set(err.error?.message || 'Erro ao realizar cadastro.');
      }
    });

  }
}
