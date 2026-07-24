import { HttpClient } from "@angular/common/http";
import { Injectable, inject } from "@angular/core";
import { RegisterRequestDto } from "../models/register-request-dto";
import { UserResponseDto } from "../models/user-response-dto";
import { Observable, tap } from "rxjs";
import { LoginRequestDto } from "../models/login-request-dto";
import { TokenResponseDto } from "../models/token-response-dto";

@Injectable({
    providedIn: 'root'
})

export class AuthService{
    private http = inject(HttpClient);

    private apiUrl = 'http://192.168.3.166:8080/api/v1/auth'

    register(dados: RegisterRequestDto): Observable<UserResponseDto> {
        return this.http.post<UserResponseDto>(`${this.apiUrl}/register`, dados);
    }

    login(dados: LoginRequestDto): Observable<TokenResponseDto> {
    return this.http.post<TokenResponseDto>(`${this.apiUrl}/login`, dados).pipe(
      tap((resposta) => {
        if (resposta.token) {
          localStorage.setItem('token', resposta.token);
        }
      })
    );
  }
}