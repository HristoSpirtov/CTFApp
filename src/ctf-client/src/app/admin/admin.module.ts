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
import { AdminEditChallengeComponent } from './admin-edit-challenge/admin-edit-challenge.component';
import { AdminFlagComponent } from './admin-flag/admin-flag.component';
import { SubmissionComponent } from './submission/submission.component';
import { AdminUserComponent } from './admin-user/admin-user.component';
import { UserSolvesComponent } from './admin-user/user-solves/user-solves.component';
import { UserFailsComponent } from './admin-user/user-fails/user-fails.component';
import { UserMissingComponent } from './admin-user/user-missing/user-missing.component';
import { UserAwardsComponent } from './admin-user/user-awards/user-awards.component';



const routes : Routes = [
  { path : '', component : AdminComponent, canActivateChild : [AdminGuard],
  children : [
    { path : '', redirectTo : 'statistics' },
    { path : 'statistics', component : StatisticsComponent },
    { path : 'users', component : AdminUsersComponent },
    { path : 'users/new', component : NewUserComponent },
    { path : 'users/:id', component : AdminUserComponent },
    { path : 'challenges', component : AdminChallengesComponent },
    { path : 'challenges/new', component : NewChallangeComponent },
    { path : 'challenges/:id', component : AdminEditChallengeComponent },
    { path : 'submissions/correct/:id', component : SubmissionComponent },
    { path : 'submissions/correct', component : SubmissionComponent },
    { path : 'submissions/incorrect', component : SubmissionComponent },
    { path : 'submissions', component : SubmissionComponent },
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
    AdminEditChallengeComponent,
    AdminFlagComponent,
    SubmissionComponent,
    AdminUserComponent,
    UserSolvesComponent,
    UserFailsComponent,
    UserMissingComponent,
    UserAwardsComponent,
  ],
  imports: [
    FormsModule,
    SharedModule,
    CommonModule,
    SharedModule,
    RouterModule.forChild(routes),
    MarkdownModule.forRoot()
  ],
})
export class AdminModule { }
