import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponent} from "./shared/components/home/home.component";
import { AuthGuard } from './guards/auth.guard';

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
    canActivate:[AuthGuard],
    loadChildren: () => import('./features/posts/posts.module').then(m =>m.PostsModule)
  },
  {
    path:"topic",
    canActivate:[AuthGuard],
    loadChildren: () => import('./features/topics/topics.module').then(m => m.TopicsModule)
  },
  {
    path:"profile",
    canActivate:[AuthGuard],
    loadChildren: () => import('./features/profile/profile.module').then(m => m.ProfileModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
