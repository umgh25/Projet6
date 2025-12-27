import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SessionService } from './services/session.service';
import { AuthService} from "./features/auth/services/auth.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit {
  title = 'front';

  constructor(private router: Router, private sessionService: SessionService, private authService: AuthService){};

  ngOnInit(): void {
    this.autolog();
  }

  private autolog () {
    if (localStorage.getItem("token")){
      this.authService.getUserInfo().subscribe(
        response => {
          this.sessionService.login(response);
          this.router.navigate(['post/list']);
        }
      )
    }
  }
}
