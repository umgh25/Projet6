import { Injectable } from '@angular/core';
import { AuthService } from '../../auth/services/auth.service';
import { AbstractControl, AsyncValidatorFn, ValidationErrors } from '@angular/forms';
import { catchError, map, Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CustomValidatorService {

  constructor(private authService: AuthService) { }

  emailTakenValidator(currentEmail:string|undefined): AsyncValidatorFn {
    return (control: AbstractControl): Observable<ValidationErrors | null> => {
      if (!control.value || control.value === currentEmail) return of(null);
      return this.authService.checkIfEmailIsAlreadyTaken(control.value).pipe(
        map(isTaken => (isTaken ? { emailTaken: true } : null)),
        catchError(() => of(null))
      );
    };
  }

  userNameTakenValidator(currentUserName:string|undefined): AsyncValidatorFn {
    return (control: AbstractControl): Observable<ValidationErrors | null> => {
      if (!control.value || control.value === currentUserName) return of(null);
      return this.authService.checkIfEmailIsAlreadyTaken(control.value).pipe(
        map(isTaken => (isTaken ? { userNameTaken: true } : null)),
        catchError(() => of(null))
      );
    };
  }
}
