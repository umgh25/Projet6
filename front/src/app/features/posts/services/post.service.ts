import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Post } from '../interfaces/post.interface';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  private pathService = 'api/post';

  constructor(private http: HttpClient) { }

  public getPosts(): Observable<Post[]> {
    return this.http.get<Post[]>(`${this.pathService}`);
  }

  public getPostById(id: string): Observable<Post> {
    return this.http.get<Post>(`${this.pathService}/${id}`)
  }
}
