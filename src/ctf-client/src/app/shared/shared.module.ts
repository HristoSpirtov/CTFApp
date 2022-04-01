import { UserService } from './service/user.service';
import { AuthenticationService } from './service/authentication.service';
import { AuthenticationGuard } from './guard/authentication.guard';
import { NotificationService } from './service/notification.service';
import { TabsModule } from 'ngx-bootstrap/tabs';
import { ButtonsModule } from 'ngx-bootstrap/buttons';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NotificationModule } from './../notification.module';
import { FormsModule } from '@angular/forms';
import { AuthInterceptor } from './interceptor/auth.interceptor';

import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { JumbotronComponent } from './jumbotron/jumbotron.component';
import { AdminJumbotronComponent } from './admin-jumbotron/admin-jumbotron.component';
import { AddComponent } from '../shared/add/add.component';



@NgModule({
  declarations: [
    JumbotronComponent,
    AdminJumbotronComponent,
    AddComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    HttpClientModule,
    NotificationModule,
    FontAwesomeModule,
    ButtonsModule,
    TabsModule
  ],
  exports : [
    JumbotronComponent,
    FormsModule,
    HttpClientModule,
    NotificationModule,
    FontAwesomeModule,
    ButtonsModule,
    TabsModule,
    AdminJumbotronComponent,
    AddComponent
  ], 
  providers : []
})
export class SharedModule { }
