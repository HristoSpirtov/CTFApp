 import { SubmissionService } from './shared/service/submission.service';
import { ChallengeService } from './shared/service/challenge.service';
import { FormsModule } from '@angular/forms';
import { AuthInterceptor } from './shared/interceptor/auth.interceptor';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { UserService } from './shared/service/user.service';
import { AuthenticationService } from './shared/service/authentication.service';
import { NotificationService } from './shared/service/notification.service';
import { SharedModule } from './shared/shared.module';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { Routes, RouterModule } from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ModalModule } from 'ngx-bootstrap/modal';


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
    FormsModule,
    
    
    RouterModule.forRoot(routes),
         BrowserAnimationsModule,
         ModalModule.forRoot()
  ],
  providers: [
    NotificationService,
    AuthenticationService, 
    UserService, 
    ChallengeService,
    SubmissionService,
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true}
  ],
  exports :[],
  bootstrap: [AppComponent]
})
export class AppModule { }
