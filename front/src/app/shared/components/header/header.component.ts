import { Component, OnInit } from '@angular/core';
import { SessionService } from '../../../services/session.service';
import { filter, Observable, of } from 'rxjs';
import { NavigationEnd, Router } from '@angular/router';
import { MobileService } from '../../../services/mobile.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent implements OnInit {
  showToolbar: boolean = true;
  isMobileResolution = false;
  isLogged: boolean = false;

  constructor(private sessionService: SessionService, private router: Router, private mobileService: MobileService
  ) {}

  ngOnInit(): void {
    this.isMobile();

    this.sessionService.$isLogged().subscribe((response) => {
      this.isLogged = response;
      this.router.events.pipe(
        filter(event => event instanceof NavigationEnd)).subscribe(() => {
        this.displayToolBar();
      });
    });
  }

  logout() {
    this.sessionService.logOut();
    this.router.navigate(['/'])
  }

  private displayToolBar() {
    this.showToolbar = (!this.isLogged && !this.isMobileResolution && (this.router.url.includes("register")) || this.router.url.includes("login")) || this.isLogged;
    console.log((this.router.url.includes("register")) || this.router.url.includes("login"))
  }

  private isMobile() {
    this.isMobileResolution = this.mobileService.isMobile();
  }
}
