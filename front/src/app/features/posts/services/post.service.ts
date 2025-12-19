import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { PostInterface } from '../interfaces/post.interface';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  private pathService = 'api/post';

  constructor(private http: HttpClient) { }

  public getPosts(): Observable<PostInterface[]> {
    return this.http.get<PostInterface[]>(`${this.pathService}`);
  }

  public getPostById(id: string): Observable<PostInterface> {
    return this.http.get<PostInterface>(`${this.pathService}/${id}`)
  }
}
