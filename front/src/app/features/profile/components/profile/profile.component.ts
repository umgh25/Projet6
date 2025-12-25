import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { map, Observable, Subject, takeUntil } from 'rxjs';
import { Topic } from '../../../posts/interfaces/topic.interface';
import { ActivatedRoute } from '@angular/router';
import { User } from '../../../auth/interfaces/user.interface';
import { SessionService } from '../../../../services/session.service';
import { TopicService } from '../../../topics/services/topic.service';
import { CustomValidatorService } from '../../services/custom-validator.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit, OnDestroy{

  constructor(private formBuilder: FormBuilder, private activatedRoute:ActivatedRoute, private sessionService: SessionService, private topicService: TopicService, private customValidatorService : CustomValidatorService){}

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
      username:[this.user?.userName ? this.user?.userName : "", {
        validators: [Validators.required],
        asyncValidators: [this.customValidatorService.userNameTakenValidator()],
        updateOn:'blur'
      }],
      email:[this.user?.email ? this.user.email : "", {
        validators: [Validators.required, Validators.email],
        asyncValidators: [this.customValidatorService.emailTakenValidator()],
        updateOn:'blur'
      }],
      password:["", Validators.required]
    })
  }

  public onSubmit(){

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


  ngOnDestroy(): void {
    this.ngUnsubscribe$.next(true);
    this.ngUnsubscribe$.complete();
  }
}
