import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { map, Observable, Subject, takeUntil } from 'rxjs';
import { Topic } from '../../../posts/interfaces/topic.interface';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../../../auth/interfaces/user.interface';
import { SessionService } from '../../../../shared/services/session.service';
import { TopicService } from '../../../topics/services/topic.service';
import { CustomValidatorService } from '../../../../shared/services/custom-validator.service';
import { ProfileService } from '../../services/profile.service';
import { FormValidationErrorService } from '../../../../shared/services/form-validation-error.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit, OnDestroy{

  constructor(private formBuilder: FormBuilder, private activatedRoute:ActivatedRoute, private sessionService: SessionService, private topicService: TopicService, private customValidatorService : CustomValidatorService, private profilService: ProfileService, public formValidationError: FormValidationErrorService, private matSnackBar: MatSnackBar, private router: Router){}

  profileForm!: FormGroup;
  userSubscribedTopics$!: Observable<Topic[]>
  user!: User|undefined;
  private ngUnsubscribe$ = new Subject<boolean>();


  ngOnInit(): void {
    this.userSubscribedTopics$ = this.activatedRoute.data.pipe(
      map(data => data['userSubscribedTopics'])
    )
    this.getUserInfo();
  }

  private initForm(){
    this.profileForm = this.formBuilder.group({
      userName:[this.user?.userName ? this.user?.userName : '', {
        validators: [Validators.required, Validators.minLength(3)],
        asyncValidators: [this.customValidatorService.userNameTakenValidator(this.user?.userName)],
      }],
      email:[this.user?.email ? this.user.email : '', {
        validators: [Validators.required, Validators.email],
        asyncValidators: [this.customValidatorService.emailTakenValidator(this.user?.email)],
      }],
      password:['', this.customValidatorService.passwordValidator()]
    })
  }

  public onSubmit(){
    this.profileForm.updateValueAndValidity();
    if(this.profileForm.valid) {
      const userUpdated = this.profileForm.getRawValue() as User;
      this.profilService.updateProfil(userUpdated).subscribe(
        response => this.exitPage()
      );
    } else {
      this.profileForm.markAllAsTouched();
    }
  }

  public topicUnSubscribe(topicId:string){
    this.topicService.topicUnsubscribe(topicId).subscribe(
      response => {
        this.userSubscribedTopics$ = this.topicService.getUserSubscribedTopics();
      }
    );
  }

  private getUserInfo(){
    this.sessionService.$user()
      .pipe(takeUntil(this.ngUnsubscribe$))
      .subscribe( response => {
        this.user = response;
        this.initForm();
      });
  }

  private exitPage(){
    this.matSnackBar.open('Compte mis à jour avec succès, veuillez vous reconnecter', 'Close', { duration: 3000 });
    this.sessionService.logOut();
    this.router.navigate(['auth/login']);
  }


  ngOnDestroy(): void {
    this.ngUnsubscribe$.next(true);
    this.ngUnsubscribe$.complete();
  }
}
