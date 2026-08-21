import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { RegisterDTO } from '../models/user.model';
import { ApiResponse } from '../models/api-response.model';

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    private http = inject(HttpClient)
    private apiUrl = 'http://localhost:8080/api/auth'

    register(user: RegisterDTO) {
        return this.http.post<ApiResponse<RegisterDTO>>
        (`${this.apiUrl}/register`, user)
    }
}
