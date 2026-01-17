import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponent} from "./components/home/home.component";
import { AuthGuard } from './guards/auth.guard';
import { alreadyLoggedInGuard } from './guards/already-logged-in.guard';
import { NotFoundComponent } from './components/not-found/not-found.component';

const routes: Routes = [
  {
    path:'',
    canActivate:[alreadyLoggedInGuard],
    component: HomeComponent
  },
  {
    path:'auth',
    loadChildren: () => import('./features/auth/auth.module').then(m => m.AuthModule)
  },
  {
    path:'post',
    canActivate:[AuthGuard],
    loadChildren: () => import('./features/posts/posts.module').then(m =>m.PostsModule)
  },
  {
    path:'topic',
    canActivate:[AuthGuard],
    loadChildren: () => import('./features/topics/topics.module').then(m => m.TopicsModule)
  },
  {
    path:'profile',
    canActivate:[AuthGuard],
    loadChildren: () => import('./features/profile/profile.module').then(m => m.ProfileModule)
  },
  {
    path:'404', component: NotFoundComponent
  },
  {
    path:'**', redirectTo:'404'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
