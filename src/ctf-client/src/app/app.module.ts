import { HomeComponent } from './home/home.component';
import { NotificationService } from './service/notification.service';
import { AuthenticationGuard } from './guard/authentication.guard';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { NavbarUserComponent } from './navbar-user/navbar-user.component';

import { ButtonsModule } from 'ngx-bootstrap/buttons';
import { TabsModule } from 'ngx-bootstrap/tabs';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { IndexComponent } from './index/index.component';
import { LoginComponent } from './login/login.component';
import { JumbotronComponent } from './jumbotron/jumbotron.component';
import { RegisterComponent } from './register/register.component';
import { Routes, RouterModule } from '@angular/router';
import { AuthenticationService } from './service/authentication.service';
import { UserService } from './service/user.service';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthInterceptor } from './interceptor/auth.interceptor';
import { NotificationModule } from './notification.module';
import { FormsModule } from '@angular/forms';


const routes: Routes = [
  { path: '', component: IndexComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'home', component:  HomeComponent},
];


@NgModule({
  declarations: [
    AppComponent,
    NavbarUserComponent,
    IndexComponent,
    LoginComponent,
    JumbotronComponent,
    RegisterComponent,
    HomeComponent
  ],
  imports: [
    FormsModule,
    HttpClientModule,
    NotificationModule,
    BrowserModule,
    ButtonsModule,
    TabsModule,
    FontAwesomeModule,
    RouterModule.forRoot(routes)
  ],
  providers: [
    NotificationService,
    AuthenticationGuard,
    AuthenticationService, 
    UserService, 
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
