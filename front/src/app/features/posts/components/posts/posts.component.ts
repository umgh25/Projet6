import { Component, OnInit } from '@angular/core';
import { Post } from '../../interfaces/post.interface';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { map, Observable } from 'rxjs';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.scss']
})
export class PostsComponent implements OnInit{
  allPosts$!: Observable<Post[]>;
  sortedPosts$!: Observable<Post[]>;
  isAscending = true;

  constructor(private activatedRoute: ActivatedRoute, private router: Router) {}

  ngOnInit(): void {
    this.allPosts$ = this.activatedRoute.data.pipe(
      map( data => data['posts'])
    );
    this.sortedPosts$ = this.allPosts$;
  }

  public onToggleSort() {
    this.isAscending = !this.isAscending;
    this.sortedPosts$ = this.allPosts$.pipe(
      map(posts => {
        const sorted = [...posts];
        return this.isAscending ? sorted : sorted.reverse();
      })
    );
  }

  public createNewPost() {
    this.router.navigate(['/post/new']);
  }
}
