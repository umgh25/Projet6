import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TopicsRoutingModule } from './topics-routing.module';
import { MatCardModule } from '@angular/material/card';
import { TopicsComponent } from './components/topics/topics.component';
import { MatButtonModule } from '@angular/material/button';


@NgModule({
  declarations: [TopicsComponent],
  imports: [
    CommonModule,
    TopicsRoutingModule,
    MatCardModule,
    MatButtonModule
  ]
})
export class TopicsModule { }
