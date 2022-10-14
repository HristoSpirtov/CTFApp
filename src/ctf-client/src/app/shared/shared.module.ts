

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
import { TextareaAutoresizeDirective } from '../shared/textarea-autoresize.directive';
import { LineBreaksPipe } from './line-breaks.pipe';
import { SortableComponent } from './sortable/sortable.component';
import { SortableModule } from 'ngx-bootstrap/sortable';
import { TextHighlightPipe } from './text-highlight.pipe';
import { FooterComponent } from './footer/footer.component';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';



@NgModule({
  declarations: [
    SortableComponent,
    JumbotronComponent,
    AdminJumbotronComponent,
    TextareaAutoresizeDirective,
    LineBreaksPipe,
    TextHighlightPipe,
    FooterComponent
  ],
  imports: [
    SortableModule.forRoot(),
    CommonModule,
    FormsModule,
    HttpClientModule,
    NotificationModule,
    FontAwesomeModule,
    ButtonsModule,
    TabsModule,
    RouterModule,
    TooltipModule,
    ModalModule,
    BsDropdownModule
  ],
  exports : [
    SortableComponent,
    TextareaAutoresizeDirective,
    JumbotronComponent,
    FormsModule,
    HttpClientModule,
    NotificationModule,
    FontAwesomeModule,
    ButtonsModule,
    TabsModule,
    AdminJumbotronComponent,
    RouterModule,
    TooltipModule,
    LineBreaksPipe,
    TextHighlightPipe,
    FooterComponent,
    BsDropdownModule
  ], 
  providers : [],
})
export class SharedModule { }
