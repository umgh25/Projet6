import { Injectable } from '@angular/core';
import { AuthService } from '../../features/auth/services/auth.service';
import {AbstractControl, AsyncValidatorFn, ValidationErrors, ValidatorFn} from '@angular/forms';
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
  passwordValidator(): ValidatorFn {
    return (control:AbstractControl) : ValidationErrors | null => {
      const value = control.value;
      if (!value) {
        return null;
      }
      const hasUpperCase = /[A-Z]+/.test(value);
      const hasLowerCase = /[a-z]+/.test(value);
      const hasNumeric = /[0-9]+/.test(value);
      const hasSpecialCharacter = /[&!=+]+/.test(value);
      const hasMinLength = value.length >= 8;
      const passwordValid = hasUpperCase && hasLowerCase && hasNumeric && hasSpecialCharacter && hasMinLength;
      return !passwordValid ? {passwordStrength:true}: null;
    }
  }

  notBlankValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const value = control.value;
      if (!value || value.trim().length === 0) {
        return { notBlank: true };
      }
      return null;
    };
  }
}
