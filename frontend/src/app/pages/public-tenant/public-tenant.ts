import { Component, inject, OnInit, signal } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { TenantService } from '../../core/services/tenant.service';
import { TreatmentService } from '../../core/services/treatment.service';
import { ThemeService } from '../../core/services/theme.service';
import { TenantResponseDTO } from '../../core/models/tenant.model';
import { TreatmentResponseDTO } from '../../core/models/treatment.model';

@Component({
  selector: 'app-public-tenant',
  imports: [RouterLink],
  templateUrl: './public-tenant.html',
  styleUrl: './public-tenant.css',
})
export class PublicTenant implements OnInit {
  private route = inject(ActivatedRoute);
  private tenantService = inject(TenantService);
  private treatmentService = inject(TreatmentService);
  public themeService = inject(ThemeService);

  tenant = signal<TenantResponseDTO | null>(null);
  treatments = signal<TreatmentResponseDTO[]>([]);
  isLoading = signal(true);
  notFound = signal(false);

  ngOnInit() {
    const slug = this.route.snapshot.paramMap.get('slug');
    if (!slug) {
      this.notFound.set(true);
      this.isLoading.set(false);
      return;
    }

    this.tenantService.getTenantBySlug(slug).subscribe({
      next: (res) => {
        this.tenant.set(res.data);
        this.loadTreatments(slug);
      },
      error: () => {
        this.notFound.set(true);
        this.isLoading.set(false);
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
}
