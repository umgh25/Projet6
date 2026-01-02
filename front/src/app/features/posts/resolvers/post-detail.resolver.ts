import { Injectable } from '@angular/core';
import {
  Resolve,
  RouterStateSnapshot,
  ActivatedRouteSnapshot,
  Router,
} from '@angular/router';
import { catchError, EMPTY, Observable, of, throwError} from 'rxjs';
import { PostService } from '../services/post.service';
import { Post } from '../interfaces/post.interface';
import { MatSnackBar } from '@angular/material/snack-bar';

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
