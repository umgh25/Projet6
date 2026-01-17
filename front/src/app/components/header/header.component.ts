import { Component, OnInit } from '@angular/core';
import { SessionService } from '../../shared/services/session.service';
import { filter, Observable, of, take } from 'rxjs';
import { NavigationEnd, Router } from '@angular/router';
import { MobileService } from '../../shared/services/mobile.service';
import { AuthService } from '../../features/auth/services/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss',]
})
export class HeaderComponent implements OnInit {
  showToolbar: boolean = true;
  isMobileResolution = false;
  isLogged: boolean = false;
  showMenu: boolean = false;

  constructor(
    private sessionService: SessionService, 
    private router: Router, 
    private mobileService: MobileService,
    private authService: AuthService
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
    // Call backend logout endpoint to invalidate the token
    this.authService.logout()
      .pipe(take(1))
      .subscribe({
        next: () => {
          // Clear session after successful backend logout
          this.sessionService.logOut();
          this.router.navigate(['/']);
        },
        error: (error) => {
          // Even if backend call fails, clear session locally
          console.error('Logout error:', error);
          this.sessionService.logOut();
          this.router.navigate(['/']);
        }
      });
  }

  private displayToolBar() {
    this.showToolbar = (!this.isLogged && !this.isMobileResolution && (this.router.url.includes("register") || this.router.url.includes("login")) || this.isLogged);
    console.log((this.router.url.includes("register")) || this.router.url.includes("login"))
  }

  private isMobile() {
    this.isMobileResolution = this.mobileService.isMobile();
  }

  public onToggleMenu () {
    this.showMenu = !this.showMenu;
  }
}
