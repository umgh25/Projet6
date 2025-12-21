import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TopicsComponent } from './components/topics/topics.component';
import { TopicsResolver } from './resolvers/topics.resolver';

const routes: Routes = [
  {
    path:"list",
    component: TopicsComponent,
    resolve:{topics:TopicsResolver}

  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TopicsRoutingModule { }
