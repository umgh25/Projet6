import { Component, OnInit } from '@angular/core';
import { map, Observable, of, take } from 'rxjs';
import { Post } from '../../interfaces/post.interface';
import { ActivatedRoute, ActivatedRouteSnapshot } from '@angular/router';
import { Comment } from '../../interfaces/comment.interface';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { PostService } from '../../services/post.service';
import { FormValidationErrorService } from '../../../../shared/services/form-validation-error.service';

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.scss'],
})
export class DetailComponent implements OnInit {
  post$!: Observable<Post>;
  comments$!: Observable<Comment[]>;
  commentForm!: FormGroup;
  postId!: string | null;

  constructor(private route: ActivatedRoute, private formBuilder: FormBuilder, private postService: PostService, public formValidationService: FormValidationErrorService) {}

  ngOnInit(): void {
    this.post$ = this.route.data.pipe(map((data) => data['post']));
    this.postId = this.route.snapshot.paramMap.get('id');
    this.initForm();
  }

  private initForm(){
    this.commentForm = this.formBuilder.group({
      postId:[this.postId, Validators.required],
      content:["", Validators.required]
    });
  }

  public onSubmit(){
    if(this.commentForm.valid){
      const newComment = this.commentForm.getRawValue() as Comment;
      console.log(newComment)
      this.postService.createComment(newComment).pipe(
        take(1)).subscribe({
        next: () => {
          this.commentForm.reset();
          this.reloadPost();
        }
      });
    } else {
      this.commentForm.markAllAsTouched();
    }
  }

  private reloadPost () {
    if(this.postId){
      this.post$ = this.postService.getPostById(this.postId);
    }
  }

  public goBack(){
    window.history.back();
  }
}
