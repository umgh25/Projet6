import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { map, Observable } from 'rxjs';
import { Topic } from '../../../posts/interfaces/topic.interface';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit{

  constructor(private formBuilder: FormBuilder, private activatedRoute:ActivatedRoute){}

  profileForm!: FormGroup;
  userSubscribedTopics$!: Observable<Topic[]>


  ngOnInit(): void {
    this.userSubscribedTopics$ = this.activatedRoute.data.pipe(
      map(data => data['userSubscribedTopics'])
    )
    this.initForm();
  }

  private initForm(){
    this.profileForm = this.formBuilder.group({
      username:["", Validators.required],
      email:["", Validators.required],
      password:["", Validators.required]
    })
  }

  public onSubmit(){

  }

  public unSubscribe(topicId:string){}
}
