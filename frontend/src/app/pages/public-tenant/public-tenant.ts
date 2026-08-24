import { Component, inject, OnInit, signal } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { TenantService } from '../../core/services/tenant';
import { TenantResponseDTO } from '../../core/models/tenant.model';

@Component({
  selector: 'app-public-tenant',
  imports: [RouterLink],
  templateUrl: './public-tenant.html',
  styleUrl: './public-tenant.css',
})
export class PublicTenant implements OnInit {
  private route = inject(ActivatedRoute);
  private tenantService = inject(TenantService);

  tenant = signal<TenantResponseDTO | null>(null);
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
        this.isLoading.set(false);
      },
      error: () => {
        this.notFound.set(true);
        this.isLoading.set(false);
      }
    });
  }
}
