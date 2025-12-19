import { Injectable } from '@angular/core';
import {
  Router,
  Resolve,
  RouterStateSnapshot,
  ActivatedRouteSnapshot,
} from '@angular/router';
import { Observable, of } from 'rxjs';
import { PostService } from '../services/post.service';
import { Post } from '../interfaces/post.interface';

@Injectable({
  providedIn: 'root',
})
export class PostDetailResolver implements Resolve<Post> {
  constructor(private postService: PostService) {}

  resolve(route: ActivatedRouteSnapshot,state: RouterStateSnapshot): Observable<Post> {
    const id = route.paramMap.get('id');

    if (!id) {
      return of();
    }

    return this.postService.getPostById(id);
  }
}
