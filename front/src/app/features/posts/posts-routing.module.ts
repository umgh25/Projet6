import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PostsComponent } from './components/posts/posts.component';
import { PostResolver } from './resolvers/post.resolver';

const routes: Routes = [
  {
    path:"list",
    component:PostsComponent,
    resolve: {posts: PostResolver}
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PostsRoutingModule { }
