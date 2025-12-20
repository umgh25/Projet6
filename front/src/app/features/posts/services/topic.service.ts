import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Topic } from '../interfaces/topic.interface';

@Injectable({
  providedIn: 'root'
})
export class TopicService {

  constructor(private http: HttpClient) { }

  private pathService = '/api/topic'

  public getTopics(): Observable<Topic[]> {
    return this.http.get<Topic[]>(`${this.pathService}/list`)
  }
}
