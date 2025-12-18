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
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
