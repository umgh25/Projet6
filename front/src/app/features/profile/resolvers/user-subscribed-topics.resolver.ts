import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';
import { Topic } from '../../posts/interfaces/topic.interface';
import { TopicService } from '../../topics/services/topic.service';

@Injectable({
  providedIn: 'root'
})
export class UserSusbcribedTopicsResolver implements Resolve<Topic[]> {
  constructor(private topicService: TopicService){}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Topic[]> {
    return this.topicService.getUserSubscribedTopics();
  }
}
