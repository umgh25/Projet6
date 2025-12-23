import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProfileComponent } from './components/profile/profile.component';
import { UserSusbcribedTopicsResolver } from './resolvers/user-subscribed-topics.resolver';

const routes: Routes = [
  {
    path:"",
    component:ProfileComponent,
    resolve:{userSubscribedTopics: UserSusbcribedTopicsResolver}
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProfileRoutingModule { }
