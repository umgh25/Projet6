import { Component, OnInit } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { filter } from 'rxjs';
import { SessionService } from './services/session.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit {

  title = 'front';
  showToolbar = true;
  isMobileResolution = false;
  isLogged = false;

  constructor(
    private router: Router,
    private sessionService: SessionService
  ) {}

  ngOnInit(): void {
    this.isMobile();

    this.sessionService.$isLogged().subscribe((response: boolean) => {
      this.isLogged = response;

      this.router.events
        .pipe(filter(event => event instanceof NavigationEnd))
        .subscribe(() => {
          this.displayToolBar();
        });
    });
  }

  private displayToolBar(): void {
    this.showToolbar =
      this.router.url !== '/' &&
      ((!this.isLogged && !this.isMobileResolution) ||
        (this.isLogged && this.isMobileResolution));
  }

  private isMobile(): void {
    this.isMobileResolution = window.innerWidth <= 768;
  }
}
