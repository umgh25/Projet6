import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import { AuthService } from '../../services/auth.service';
import { AuthSuccess } from '../../interfaces/auth-success';
import { SessionService } from '../../../../services/session.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  registerForm!: FormGroup;

  constructor(private formBuilder: FormBuilder, private authService: AuthService, private sessionService : SessionService, private router: Router) {}

  ngOnInit(): void {
    this.initForm();
  }

  private initForm() {
    this.registerForm = this.formBuilder.group({
      userName:["", [Validators.required]],
      email:["", [Validators.required]],
      password:["", [Validators.required]]
    })
  }

  onSubmit(): void {
    if (this.registerForm.valid) {
      const registerRequest = this.registerForm.getRawValue();
      this.authService.register(registerRequest).subscribe((response:AuthSuccess) => {
        localStorage.setItem('token', response.token);
        this.sessionService.login();
        this.router.navigate(['/post/list'])
      })
    } else {
      this.registerForm.markAllAsTouched();
    }
  }

}
