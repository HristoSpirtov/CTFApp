import { AuthInterceptor } from './shared/interceptor/auth.interceptor';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { UserService } from './shared/service/user.service';
import { AuthenticationService } from './shared/service/authentication.service';
import { NotificationService } from './shared/service/notification.service';
import { SharedModule } from './shared/shared.module';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { Routes, RouterModule } from '@angular/router';


const routes: Routes = [
  { path: '', loadChildren: () => import('./user/user.module').then(m => m.UserModule )}
];

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    SharedModule,
    BrowserModule,
    RouterModule.forRoot(routes)
  ],
  providers: [
    NotificationService,
    AuthenticationService, 
    UserService, 
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true}
  ],
  exports :[],
  bootstrap: [AppComponent]
})
export class AppModule { }
