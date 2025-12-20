import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PostsComponent } from './components/posts/posts.component';
import { PostResolver } from './resolvers/post.resolver';
import { DetailComponent } from './components/detail/detail.component';
import { PostDetailResolver } from './resolvers/post-detail.resolver';
import { NewPostComponent } from './components/new/new-post/new-post.component';

const routes: Routes = [
  {
    path:'list',
    component:PostsComponent,
    resolve: {posts: PostResolver}
  },
  {
    path:'new',
    component: NewPostComponent
  },
  {
    path:':id',
    component: DetailComponent,
    resolve: {post: PostDetailResolver}
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PostsRoutingModule { }
