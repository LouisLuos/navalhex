import { Component, inject, OnInit, signal } from '@angular/core';
import { Router } from '@angular/router';
import { TenantService } from '../../core/services/tenant.service';
import { TreatmentService } from '../../core/services/treatment.service';
import { ThemeService } from '../../core/services/theme.service';
import { TenantResponseDTO } from '../../core/models/tenant.model';
import { TreatmentResponseDTO, RegisterTreatmentDTO, UpdateTreatmentDTO } from '../../core/models/treatment.model';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CurrencyPipe } from '@angular/common';

@Component({
  selector: 'app-dashboard',
  imports: [ReactiveFormsModule, CurrencyPipe],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard implements OnInit {
  private router = inject(Router);
  private tenantService = inject(TenantService);
  private treatmentService = inject(TreatmentService);
  public themeService = inject(ThemeService);

  // Estados com Signals
  tenant = signal<TenantResponseDTO | null>(null);
  treatments = signal<TreatmentResponseDTO[]>([]);
  isLoading = signal(true);
  isSubmitting = signal(false);
  errorMessage = signal<string | null>(null);
  copiedSlug = signal(false);

  // Estado do Modal (Novo / Edição)
  isModalOpen = signal(false);
  editingTreatmentId = signal<string | null>(null);

  // Formulário Reativo de Serviço
  treatmentForm = new FormGroup({
    title: new FormControl('', { nonNullable: true, validators: [Validators.required, Validators.minLength(3), Validators.maxLength(30)] }),
    description: new FormControl('', { nonNullable: true, validators: [Validators.required, Validators.minLength(10), Validators.maxLength(255)] }),
    price: new FormControl<number | null>(null, { nonNullable: true, validators: [Validators.required, Validators.min(0.01)] }),
    durationMinutes: new FormControl<number | null>(30, { nonNullable: true, validators: [Validators.required, Validators.min(5)] }),
  });

  ngOnInit() {
    this.loadTenantData();
  }

  loadTenantData() {
    this.isLoading.set(true);
    this.tenantService.getMyTenant().subscribe({
      next: (res) => {
        if (res.data) {
          this.tenant.set(res.data);
          this.loadTreatments(res.data.slug);
        } else {
          this.router.navigate(['/onboarding']);
        }
      },
      error: () => {
        this.isLoading.set(false);
        this.router.navigate(['/onboarding']);
      }
    });
  }

  loadTreatments(slug: string) {
    this.treatmentService.getAllTreatments(slug).subscribe({
      next: (res) => {
        this.treatments.set(res.data || []);
        this.isLoading.set(false);
      },
      error: () => {
        this.isLoading.set(false);
      }
    });
  }

  openCreateModal() {
    this.editingTreatmentId.set(null);
    this.treatmentForm.reset({
      title: '',
      description: '',
      price: null,
      durationMinutes: 30
    });
    this.errorMessage.set(null);
    this.isModalOpen.set(true);
  }

  openEditModal(treatment: TreatmentResponseDTO) {
    this.editingTreatmentId.set(treatment.id);
    this.treatmentForm.patchValue({
      title: treatment.title,
      description: treatment.description,
      price: treatment.price,
      durationMinutes: treatment.durationMinutes
    });
    this.errorMessage.set(null);
    this.isModalOpen.set(true);
  }

  closeModal() {
    this.isModalOpen.set(false);
    this.editingTreatmentId.set(null);
    this.treatmentForm.reset();
  }

  saveTreatment() {
    if (this.treatmentForm.invalid) return;

    const currentTenant = this.tenant();
    if (!currentTenant) return;

    this.isSubmitting.set(true);
    this.errorMessage.set(null);

    const formValues = this.treatmentForm.getRawValue();

    if (this.editingTreatmentId()) {
      // Atualização
      const updatePayload: UpdateTreatmentDTO = {
        id: this.editingTreatmentId()!,
        title: formValues.title,
        description: formValues.description,
        price: formValues.price!,
        durationMinutes: formValues.durationMinutes!
      };

      this.treatmentService.updateTreatment(this.editingTreatmentId()!, updatePayload, currentTenant.slug).subscribe({
        next: () => {
          this.isSubmitting.set(false);
          this.closeModal();
          this.loadTreatments(currentTenant.slug);
        },
        error: (err) => {
          this.isSubmitting.set(false);
          this.errorMessage.set(err.error?.message || 'Erro ao atualizar serviço.');
        }
      });
    } else {
      // Criação
      const createPayload: RegisterTreatmentDTO = {
        title: formValues.title,
        description: formValues.description,
        price: formValues.price!,
        durationMinutes: formValues.durationMinutes!
      };

      this.treatmentService.registerTreatment(createPayload, currentTenant.slug).subscribe({
        next: () => {
          this.isSubmitting.set(false);
          this.closeModal();
          this.loadTreatments(currentTenant.slug);
        },
        error: (err) => {
          this.isSubmitting.set(false);
          this.errorMessage.set(err.error?.message || 'Erro ao cadastrar serviço.');
        }
      });
    }
  }

  deleteTreatment(id: string) {
    const currentTenant = this.tenant();
    if (!currentTenant) return;

    if (confirm('Tem certeza que deseja remover este serviço do catálogo?')) {
      this.treatmentService.deleteTreatment(id, currentTenant.slug).subscribe({
        next: () => {
          this.loadTreatments(currentTenant.slug);
        },
        error: (err) => {
          alert(err.error?.message || 'Erro ao excluir serviço.');
        }
      });
    }
  }

  copyPublicLink() {
    const slug = this.tenant()?.slug;
    if (slug) {
      const url = `${window.location.origin}/${slug}`;
      navigator.clipboard.writeText(url);
      this.copiedSlug.set(true);
      setTimeout(() => this.copiedSlug.set(false), 2000);
    }
  }

  logout() {
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
  }
}
