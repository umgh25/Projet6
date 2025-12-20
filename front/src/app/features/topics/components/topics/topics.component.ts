import { Component, OnInit } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Topic } from '../../../posts/interfaces/topic.interface';
import { ActivatedRoute } from '@angular/router';
import { TopicService } from '../../services/topic.service';

@Component({
  selector: 'app-topics',
  templateUrl: './topics.component.html',
  styleUrls: ['./topics.component.scss'],
})
export class TopicsComponent implements OnInit{

  topics$!: Observable<Topic[]>

  constructor(private activatedRoute: ActivatedRoute, private topicService: TopicService) {}

  ngOnInit(): void {
    this.topics$ = this.activatedRoute.data.pipe(
      map(data => data['topics'])
    );
  }

}
