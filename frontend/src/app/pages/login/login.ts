import { Component, inject, signal } from '@angular/core';
import { AuthService } from '../../core/services/auth';
import { TenantService } from '../../core/services/tenant';
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
  private authService = inject(AuthService);
  private tenantService = inject(TenantService);
  private router = inject(Router);

  isLoading = signal(false);
  errorMessage = signal<string | null>(null);

  loginForm = new FormGroup({
    email: new FormControl('', { nonNullable: true, validators: [Validators.required, Validators.email] }),
    password: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
  });

  onsubmit() {
    if (this.loginForm.invalid) return;

    this.isLoading.set(true);
    this.errorMessage.set(null);

    this.authService.login(this.loginForm.getRawValue() as LoginDTO).subscribe({
      next: (response) => {
        const user = response.data;

        if (user.role === 'TENANT') {
          // Checa se o tenant já possui uma barbearia cadastrada
          this.tenantService.getMyTenant().subscribe({
            next: (tenantRes) => {
              this.isLoading.set(false);
              if (tenantRes.data) {
                // Já possui barbearia -> Painel
                this.router.navigate(['/dashboard']);
              } else {
                // Primeiro acesso sem barbearia -> Onboarding
                this.router.navigate(['/onboarding']);
              }
            },
            error: () => {
              this.isLoading.set(false);
              this.router.navigate(['/onboarding']);
            }
          });
        } else {
          this.isLoading.set(false);
          this.router.navigate(['/dashboard']);
        }
      },
      error: (err) => {
        this.isLoading.set(false);
        console.error('Erro no login:', err);
        this.errorMessage.set(err.error?.message || 'Erro ao realizar login.');
      }
    });
  }
}

