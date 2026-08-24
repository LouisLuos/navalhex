import { Component, inject, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { TenantService } from '../../core/services/tenant';
import { RegisterTenantDTO } from '../../core/models/tenant.model';

@Component({
  selector: 'app-onboarding',
  imports: [ReactiveFormsModule],
  templateUrl: './onboarding.html',
  styleUrl: './onboarding.css',
})
export class Onboarding {
  private tenantService = inject(TenantService);
  private router = inject(Router);

  isLoading = signal(false);
  errorMessage = signal<string | null>(null);

  tenantForm = new FormGroup({
    companyName: new FormControl('', { nonNullable: true, validators: [Validators.required, Validators.minLength(3)] }),
    slug: new FormControl('', { nonNullable: true, validators: [Validators.required, Validators.minLength(3), Validators.pattern(/^[a-z0-9-]+$/)] }),
    openingHours: new FormControl('08:00', { nonNullable: true, validators: [Validators.required] }),
    closingHours: new FormControl('20:00', { nonNullable: true, validators: [Validators.required] }),
    whatsapp: new FormControl('', { nonNullable: true, validators: [Validators.required, Validators.minLength(11)] }),
    companyAddress: new FormControl('', { nonNullable: true, validators: [Validators.required, Validators.minLength(5)] }),
  });

  // Gera slug automaticamente ao digitar o nome da barbearia se o slug ainda não foi editado
  onNameChange() {
    const name = this.tenantForm.controls.companyName.value;
    const generatedSlug = name
      .toLowerCase()
      .trim()
      .normalize('NFD')
      .replace(/[\u0300-\u036f]/g, '') // remove acentos
      .replace(/[^a-z0-9\s-]/g, '')
      .replace(/\s+/g, '-');

    if (!this.tenantForm.controls.slug.dirty) {
      this.tenantForm.controls.slug.setValue(generatedSlug);
    }
  }

  onSubmit() {
    if (this.tenantForm.invalid) return;

    this.isLoading.set(true);
    this.errorMessage.set(null);

    const formValues = this.tenantForm.getRawValue();

    // Garante que o formato de hora seja HH:mm:ss esperado pelo LocalTime do backend
    const payload: RegisterTenantDTO = {
      ...formValues,
      openingHours: formValues.openingHours.length === 5 ? `${formValues.openingHours}:00` : formValues.openingHours,
      closingHours: formValues.closingHours.length === 5 ? `${formValues.closingHours}:00` : formValues.closingHours,
    };

    this.tenantService.registerTenant(payload).subscribe({
      next: () => {
        this.isLoading.set(false);
        // Redireciona para o painel principal
        this.router.navigate(['/dashboard']);
      },
      error: (err) => {
        this.isLoading.set(false);
        this.errorMessage.set(err.error?.message || 'Erro ao cadastrar barbearia.');
      }
    });
  }
}
