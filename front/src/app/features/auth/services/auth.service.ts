import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthSuccess } from '../interfaces/auth-success';
import { LoginRequest } from '../interfaces/loginRequest.interface';
import { RegisterRequest } from '../interfaces/register-request';
import { User } from '../interfaces/user.interface';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private pathService = 'api/auth';

  constructor(private http : HttpClient) {}

  public register(registerRequest: RegisterRequest): Observable<AuthSuccess> {
    return this.http.post<AuthSuccess>(`${this.pathService}/register`, registerRequest);
  }

  public login(loginRequest: LoginRequest): Observable<AuthSuccess> {
    return this.http.post<AuthSuccess>(`${this.pathService}/login`, loginRequest);
  }

  public getUserInfo(): Observable<User> {
    return this.http.get<User>(`${this.pathService}/me`);
  }
  public checkIfEmailIsAlreadyTaken(email:string): Observable<boolean>{
    return this.http.get<boolean>(`${this.pathService}/email/${email}`);
  }

  public checkIfUserNameIslAlreadyTaken(userName:string): Observable<boolean>{
    return this.http.get<boolean>(`${this.pathService}/username/${userName}`);
  }
}
