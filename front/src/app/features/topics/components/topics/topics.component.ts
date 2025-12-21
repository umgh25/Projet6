import { Component, OnInit } from '@angular/core';
import { map, Observable, take } from 'rxjs';
import { Topic } from '../../../posts/interfaces/topic.interface';
import { ActivatedRoute, Router } from '@angular/router';
import { TopicService } from '../../services/topic.service';

@Component({
  selector: 'app-topics',
  templateUrl: './topics.component.html',
  styleUrls: ['./topics.component.scss'],
})
export class TopicsComponent implements OnInit {

  topics$!: Observable<Topic[]>;

  constructor(
    private activatedRoute: ActivatedRoute,
    private topicService: TopicService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.topics$ = this.activatedRoute.data.pipe(
      map(data => data['topics'])
    );
  }

  public subscribe(topicId: string): void {
    this.topicService.subscribe(topicId)
      .pipe(take(1))
      .subscribe(() => {
        this.reloadTopics();
      });
  }

  private reloadTopics(): void {
    this.topics$ = this.topicService.getAllTopicsWithSubscriptionStatus();
  }
}
