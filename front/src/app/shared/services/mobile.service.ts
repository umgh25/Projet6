import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MobileService {

  constructor() { }

  public isMobile () {
    return window.innerWidth <=768;
  }

}
