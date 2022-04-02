import { ModalModule } from 'ngx-bootstrap/modal';
import { TooltipModule } from 'ngx-bootstrap/tooltip';
import { RouterModule } from '@angular/router';
import { TabsModule } from 'ngx-bootstrap/tabs';
import { ButtonsModule } from 'ngx-bootstrap/buttons';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NotificationModule } from './../notification.module';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { JumbotronComponent } from './jumbotron/jumbotron.component';
import { AdminJumbotronComponent } from './admin-jumbotron/admin-jumbotron.component';



@NgModule({
  declarations: [
    JumbotronComponent,
    AdminJumbotronComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    HttpClientModule,
    NotificationModule,
    FontAwesomeModule,
    ButtonsModule,
    TabsModule,
    RouterModule,
    TooltipModule,
    ModalModule
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
    RouterModule,
    TooltipModule
  ], 
  providers : []
})
export class SharedModule { }
