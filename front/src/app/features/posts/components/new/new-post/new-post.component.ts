import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { map, Observable, startWith, take } from 'rxjs';
import { TopicService } from '../../../../topics/services/topic.service';
import { Topic } from '../../../interfaces/topic.interface';
import { CreatePost } from '../../../interfaces/createPost.interface';
import { PostService } from '../../../services/post.service';
import { Router } from '@angular/router';
import { FormValidationErrorService } from '../../../../../shared/services/form-validation-error.service';

@Component({
  selector: 'app-new-post',
  templateUrl: './new-post.component.html',
  styleUrls: ['./new-post.component.scss',]
})
export class NewPostComponent implements OnInit {
  newPostForm!: FormGroup;
  topics$!: Observable<Topic[]>;

  constructor(
    private formBuilder: FormBuilder,
    private topicService: TopicService,
    private postService: PostService,
    private router: Router,
    public formValidationError: FormValidationErrorService
  ) {}

  ngOnInit(): void {
    this.getTopics();
    this.initForm();
  }

  private getTopics() {
    this.topics$ = this.topicService.getTopics();
  }

  private initForm() {
    this.newPostForm = this.formBuilder.group({
      topicId: ['', Validators.required],
      title: ['', Validators.required],
      content: ['', Validators.required],
    });
  }

  public onSubmit() {
    if (this.newPostForm.valid) {
      const newPost = this.newPostForm.getRawValue();
      this.postService
        .createNewPost(newPost)
        .pipe(take(1))
        .subscribe(() => {
          this.router.navigate(['/post/list']);
        });
    } else {
      this.newPostForm.markAllAsTouched();
    }
  }

  public goBack(){
    window.history.back();
  }
}
