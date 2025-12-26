import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../../auth/interfaces/user.interface';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  constructor(private http: HttpClient) { }

  private pathService = '/api/user'

  public updateProfil(user: User): Observable<void> {
    return this.http.post<void>(`${this.pathService}`, user)
  }
}
