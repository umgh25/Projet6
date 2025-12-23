import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponent} from "./shared/components/home/home.component";

const routes: Routes = [
  {
    path:'',
    component: HomeComponent
  },
  {
    path:"auth",
    loadChildren: () => import('./features/auth/auth.module').then(m => m.AuthModule)
  },
  {
    path:"post",
    loadChildren: () => import('./features/posts/posts.module').then(m =>m.PostsModule)
  },
  {
    path:"topic",
    loadChildren: () => import('./features/topics/topics.module').then(m => m.TopicsModule)
  },
  {
    path:"profile",
    loadChildren: () => import('./features/profile/profile.module').then(m => m.ProfileModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
