import { NavbarUserComponent } from './navbar-user/navbar-user.component';
import { AuthenticationGuard } from './../shared/guard/authentication.guard';
import { ChallengeComponent } from './challenge/challenge.component';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';

import { IndexComponent } from './index/index.component';

import { UserComponent } from './user/user.component';
import { Routes, RouterModule } from '@angular/router';
import { SharedModule } from './../shared/shared.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ScoreboardComponent } from './scoreboard/scoreboard.component';

const routes : Routes = [
  { path : '', component : UserComponent,
  children : [
    { path : '', redirectTo : 'index' },
    { path : 'index', component : IndexComponent },
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent } ,
    { path: 'challenges', component:  ChallengeComponent, canActivate : [AuthenticationGuard]},
    { path: 'scoreboard', component:  ScoreboardComponent, canActivate : [AuthenticationGuard]},
    { path: 'admin', loadChildren: () => import('./../admin/admin.module').then(m => m.AdminModule )},

  ]}
]

@NgModule({
  declarations: [
    UserComponent,
    NavbarUserComponent,
    IndexComponent,
    LoginComponent,
    RegisterComponent,
    ChallengeComponent,
    ScoreboardComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    RouterModule.forChild(routes)
  ]
})
export class UserModule { }
