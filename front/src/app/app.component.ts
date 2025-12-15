import { Component, OnInit } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { filter, Observable, of } from 'rxjs';
import { SessionService } from './services/session.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent implements OnInit {
  title = 'front';
  showToolbar: boolean = true;
  isMobileResolution = false;
  isLogged: boolean = false;

  constructor(private router: Router, private sessionService: SessionService) {}

  ngOnInit(): void {
    this.isMobile();
    this.sessionService.$isLogged().subscribe((response) => {
      this.isLogged = response;
      this.router.events
        .pipe(filter((event) => event instanceof NavigationEnd))
        .subscribe(() => {
          this.displayToolBar();
        });
    });
  }

  private displayToolBar() {
    this.showToolbar =
      this.router.url !== '/' &&
      ((!this.isLogged && !this.isMobileResolution) ||
        (this.isLogged && this.isMobileResolution));
  }

  private isMobile() {
    this.isMobileResolution = window.innerWidth <= 768;
  }
}
