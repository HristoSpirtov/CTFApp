import { HeaderType } from './../enum/header-type.enum';
import { NotificationType } from './../enum/notification-type.enum';
import { User } from './../model/user';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { NotificationService } from './../service/notification.service';
import { AuthenticationService } from './../service/authentication.service';
import { Router } from '@angular/router';
import { Component, ElementRef, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { Subscription, take } from 'rxjs';

@Component({
  selector: 'login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  
  constructor(private router : Router, private authenticationService : AuthenticationService, private notificationService : NotificationService) { }
  

  ngOnInit(): void {
    if (this.authenticationService.isUserLoggedIn()) {
      this.router.navigateByUrl('/home')
    } else {
      this.router.navigateByUrl('/login')
    }
  }

  onLogin(loginForm: any) {
    this.authenticationService.login(loginForm).pipe(take(1)).subscribe({
        next: (response : HttpResponse<User>) => {
        const token = response.headers.get(HeaderType.JWT_TOKEN)!;
        this.authenticationService.saveToken(token);
        this.authenticationService.addUserToCash((response as HttpResponse<User>).body!)
        this.router.navigateByUrl('/home');
      },     
        error: (errorResponse: HttpErrorResponse) => {
        console.log(errorResponse);
        this.seErrorNotification(NotificationType.ERROR, errorResponse.error.message)
      }
    });
  }

  private seErrorNotification(notificationType: NotificationType, message: string) : void {
    if (message) {
      console.log("sdasdas")
      this.notificationService.notify(notificationType, message);
    } else {
      this.notificationService.notify(notificationType, 'An error occured. Please try again.');
    }
  }


}
