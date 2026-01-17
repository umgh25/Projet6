import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot } from '@angular/router';
import { catchError, EMPTY, Observable, throwError } from 'rxjs';
import { Post } from '../interfaces/post.interface';
import { PostService } from '../services/post.service';

@Injectable({
  providedIn: 'root',
})
export class PostDetailResolver implements Resolve<Post> {
  constructor(private postService: PostService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot,state: RouterStateSnapshot): Observable<Post> {
    const id = route.paramMap.get('id');

    if (!id) {
      return EMPTY;
    }

    return this.postService.getPostById(id).pipe(
      catchError(error => {
        if(error.status === 404) {
          this.router.navigate(['404']);
        }
        return throwError(() => error);
      })
    );
  }
}
