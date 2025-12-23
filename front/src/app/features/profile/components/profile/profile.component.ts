import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { map, Observable, Subject, takeUntil } from 'rxjs';
import { Topic } from '../../../posts/interfaces/topic.interface';
import { ActivatedRoute } from '@angular/router';
import { User } from '../../../auth/interfaces/user.interface';
import { SessionService } from '../../../../services/session.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit, OnDestroy{

  constructor(private formBuilder: FormBuilder, private activatedRoute:ActivatedRoute, private sessionService: SessionService){}

  profileForm!: FormGroup;
  userSubscribedTopics$!: Observable<Topic[]>
  user!: User|undefined;
  private ngUnsubscribe$ = new Subject<boolean>();


  ngOnInit(): void {
    this.userSubscribedTopics$ = this.activatedRoute.data.pipe(
      map(data => data['userSubscribedTopics'])
    )
    this.getUserInfo();
    this.initForm();
  }

  private initForm(){
    this.profileForm = this.formBuilder.group({
      username:[this.user?.userName ? this.user?.userName : "", Validators.required],
      email:[this.user?.email ? this.user.email : "", Validators.required],
      password:["", Validators.required]
    })
  }

  public onSubmit(){

  }

  public unSubscribe(topicId:string){}

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
