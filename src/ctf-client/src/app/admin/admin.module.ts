import { FormsModule } from '@angular/forms';
import { MarkdownModule } from 'ngx-markdown';
import { NewChallangeComponent } from './new-challange/new-challange.component';
import { AdminGuard } from '../shared/guard/admin.guard';
import { StatisticsComponent } from './statistics/statistics.component';
import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminComponent } from '../admin/admin/admin.component';
import { NavbarAdminComponent } from './navbar-admin/navbar-admin.component';
import { AdminUsersComponent } from '../admin/admin-users/admin-users.component';
import { SharedModule } from '../shared/shared.module';
import { NewUserComponent } from './new-user/new-user.component';
import { AdminChallengesComponent } from './admin-challenges/admin-challenges.component';



import 'prismjs';
import 'prismjs/components/prism-typescript.min.js';
import 'prismjs/plugins/line-numbers/prism-line-numbers.js';
import 'prismjs/plugins/line-highlight/prism-line-highlight.js';


const routes : Routes = [
  { path : '', component : AdminComponent, canActivateChild : [AdminGuard],
  children : [
    { path : '', redirectTo : 'statistics' },
    { path : 'statistics', component : StatisticsComponent },
    { path : 'users', component : AdminUsersComponent },
    { path : 'users/new', component : NewUserComponent },
    { path : 'challenges', component : AdminChallengesComponent },
    { path : 'challenges/new', component : NewChallangeComponent },
  ]}
]

@NgModule({
  declarations: [
    AdminComponent,
    NavbarAdminComponent,
    StatisticsComponent,
    AdminUsersComponent,
    AdminChallengesComponent,
    NewChallangeComponent,
    NewUserComponent,
  
  ],
  imports: [
    FormsModule,
    SharedModule,
    CommonModule,
    SharedModule,
    RouterModule.forChild(routes),
    MarkdownModule.forRoot()
  ]
})
export class AdminModule { }
