import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { RegisterTreatmentDTO, TreatmentResponseDTO, UpdateTreatmentDTO } from '../models/treatment.model';
import { ApiResponse } from '../models/api-response.model';
import { environment } from '../../../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class TreatmentService {
    private http = inject(HttpClient);
    private apiUrl = `${environment.apiUrl}/api/tenants`;

    private getUrl(slug: string) {
        return `${this.apiUrl}/${slug}/treatments`;
    }

    registerTreatment(treatment: RegisterTreatmentDTO, slug: string) {
        return this.http.post<ApiResponse<void>>(this.getUrl(slug), treatment);
    }

    getAllTreatments(slug: string) {
        return this.http.get<ApiResponse<TreatmentResponseDTO[]>>(this.getUrl(slug));
    }

    updateTreatment(id: string, treatment: UpdateTreatmentDTO, slug: string) {
        return this.http.put<ApiResponse<void>>(`${this.getUrl(slug)}/${id}`, treatment);
    }

    deleteTreatment(id: string, slug: string) {
        return this.http.delete<ApiResponse<void>>(`${this.getUrl(slug)}/${id}`);
    }
}
