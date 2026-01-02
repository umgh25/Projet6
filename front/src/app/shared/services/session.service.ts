import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { User } from '../../features/auth/interfaces/user.interface';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  public isLogged = false;

  private isLoggedSubject = new BehaviorSubject<boolean>(this.isLogged);

  private user: User|undefined = undefined;

  private userSubject = new BehaviorSubject<User|undefined>(this.user);

  constructor() { }

  public $isLogged(): Observable<boolean> {
    return this.isLoggedSubject.asObservable();
  }

  public $user(): Observable<User|undefined> {
    return this.userSubject.asObservable();
  }

  public login(user:User|undefined):void {
    this.user = user;
    this.isLogged = true;
    this.next();
  }

  public logOut(): void {
    localStorage.removeItem('token');
    this.isLogged = false;
    this.next();
  }

  private next(): void {
    this.isLoggedSubject.next(this.isLogged);
    this.userSubject.next(this.user);
  }
}
