import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { RegisterTenantDTO, TenantResponseDTO } from '../models/tenant.model';
import { ApiResponse } from '../models/api-response.model';
import { environment } from '../../../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class TenantService {
    private http = inject(HttpClient);
    private apiUrl = `${environment.apiUrl}/api/tenants`;

    registerTenant(tenant: RegisterTenantDTO) {
        return this.http.post<ApiResponse<void>>(`${this.apiUrl}/register`, tenant);
    }

    getMyTenant() {
        return this.http.get<ApiResponse<TenantResponseDTO>>(`${this.apiUrl}/me`);
    }
    
    getTenantBySlug(slug: string) {
        return this.http.get<ApiResponse<TenantResponseDTO>>(`${this.apiUrl}/${slug}`);
    }
}
