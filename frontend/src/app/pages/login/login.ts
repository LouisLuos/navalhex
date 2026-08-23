import { Component, inject, signal } from '@angular/core';
import { AuthService } from '../../core/services/auth';
import { Router, RouterLink } from '@angular/router';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { LoginDTO } from '../../core/models/user.model';

@Component({
  imports: [ReactiveFormsModule, RouterLink],
  selector: 'app-login',
  styleUrl: './login.css',
  templateUrl: './login.html',
})
export class Login {
  private authService = inject(AuthService)
  private router = inject(Router)

  isLoading = signal(false)
  errorMessage = signal<string | null>(null)

  loginForm = new FormGroup({
    email: new FormControl('', { nonNullable: true, validators: [Validators.required, Validators.email] }),
    password: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
  })

  onsubmit() {
    console.log(this.loginForm.value)
    this.isLoading.set(true)
    this.authService.login(this.loginForm.value as LoginDTO).subscribe({
      next: (response) => {
        this.isLoading.set(false)
        console.log('Sucesso:', response)
        this.router.navigate(['/dashboard'])
      },
      error: (err) => {
        this.isLoading.set(false)
        console.error('Erro na requisição:', err)
        this.errorMessage.set(err.error?.message || 'Erro ao realizar login.')
      }
    })
  }
}
