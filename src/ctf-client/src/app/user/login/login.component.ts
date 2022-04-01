import { User } from '../../shared/model/user';
import { HeaderType } from '../../shared/enum/header-type.enum';
import { NotificationType } from '../../shared/enum/notification-type.enum';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { NotificationService } from '../../shared/service/notification.service';
import { AuthenticationService } from '../../shared/service/authentication.service';
import { Router } from '@angular/router';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { take, Subscription } from 'rxjs';

@Component({
  selector: 'login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, OnDestroy {
  isLoggedIn!: boolean;
  subscription : Subscription

  
  constructor(private router : Router, private authenticationService : AuthenticationService, private notificationService : NotificationService) {
    this.subscription = this.authenticationService.isLoggedIn().subscribe(x => {
      this.isLoggedIn = x;
    })
  }
  
  

  ngOnInit(): void {
    if (this.isLoggedIn) {
      this.router.navigateByUrl('/challenges')
    } 
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  onLogin(loginForm: any) {
    
    this.authenticationService.login(loginForm).pipe(take(1)).subscribe({
        next: (response : HttpResponse<User>) => {
          const token = response.headers.get(HeaderType.JWT_TOKEN)!;
          const refreshToken = response.headers.get(HeaderType.REFRESH_TOKEN)!;
          this.authenticationService.saveJwtToken(token);
          this.authenticationService.saveRefreshToken(refreshToken);
          this.authenticationService.saveUser(response.body!);
          this.authenticationService.isLoginSubject.next(true);
          this.authenticationService.userSubject.next(response.body! as User);
          this.setNotification(NotificationType.SUCCESS,  `User ${ response.body?.username } successfuly logged in`); 
          this.router.navigateByUrl('/challenges');
          
      },     
        error: (errorResponse: HttpErrorResponse) => {
          this.authenticationService.isLoginSubject.next(false);
          this.setNotification(NotificationType.ERROR, errorResponse.error.message);
      }
    });
  }


  private setNotification(notificationType: NotificationType, message: string) : void {
    if (message) {
      this.notificationService.notify(notificationType, message);
    } else {
      this.notificationService.notify(notificationType, 'An error occured. Please try again.');
    }
  }

  private isAdmin(user : User)  {
    return user.roles.length == 2;
  }


}
