import { Injectable } from '@angular/core';
import {
  Router, Resolve,
  RouterStateSnapshot,
  ActivatedRouteSnapshot
} from '@angular/router';
import { Observable, of } from 'rxjs';
import { TopicService } from '../../topics/services/topic.service';
import { Topic } from '../../posts/interfaces/topic.interface';

@Injectable({
  providedIn: 'root'
})
export class UserSusbcribedTopicsResolver implements Resolve<Topic[]> {
  constructor(private topicService: TopicService){}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Topic[]> {
    return this.topicService.getUserSubscribedTopics();
  }
}
