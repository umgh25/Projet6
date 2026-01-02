import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { CanActivateFn } from '@angular/router';
import { SessionService } from '../shared/services/session.service';

export const alreadyLoggedInGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const sessionService = inject(SessionService);

  const token = localStorage.getItem('token');

  if (token) {
    router.navigate(['post/list']);
    return false;
  }

  return true;
};
