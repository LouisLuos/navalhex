import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { LoginDTO, LoginResponseDTO, RegisterDTO } from '../models/user.model';
import { ApiResponse } from '../models/api-response.model';
import { environment } from '../../../environments/environment';
import { tap } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    private http = inject(HttpClient);
    private apiUrl = `${environment.apiUrl}/api/auth`;

    register(user: RegisterDTO) {
        return this.http.post<ApiResponse<void>>(`${this.apiUrl}/register`, user);
    }

    login(user: LoginDTO) {
        return this.http.post<ApiResponse<LoginResponseDTO>>(`${this.apiUrl}/login`, user).pipe(
            tap(response => {
                localStorage.setItem('token', response.data.token);
            })
        )
    }
}

