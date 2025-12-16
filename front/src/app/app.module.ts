import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { HomeComponent } from './shared/components/home/home.component';
import { NgOptimizedImage } from "@angular/common";
import { HeaderComponent } from './shared/components/header/header.component';
import  {MatToolbarModule } from "@angular/material/toolbar";
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { jwtInterceptor } from './interceptors/jwt.interceptor';


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    HeaderComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgOptimizedImage,
    MatToolbarModule
  ],
  providers: [
    provideHttpClient(withInterceptors([jwtInterceptor])),
    provideAnimationsAsync(),
    provideHttpClient()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
