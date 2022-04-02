import { User } from '../../shared/model/user';
import { Subscription } from 'rxjs';
import { NotificationType } from '../../shared/enum/notification-type.enum';
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { NotificationService } from '../../shared/service/notification.service';
import { AuthenticationService } from '../../shared/service/authentication.service';
import { Component, OnInit } from '@angular/core';
import { faEyeSlash, faEye } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  faEyeSlash = faEyeSlash;
  faEye = faEye;

  showPassword! : boolean;
  inputType! : string;

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
    this.showPassword = false;
    this.inputType = "password";
  }

  

  onRegister(registerForm : User) {
    this.authenticationService.register(registerForm).subscribe({
      next: (user : User) => {
        this.seErrorNotification(NotificationType.SUCCESS, `A new user was created with username ${user.username}`)
      this.router.navigateByUrl('/login');
    },     
      error: (errorResponse: HttpErrorResponse) => {
      console.log(errorResponse);
      this.seErrorNotification(NotificationType.ERROR, errorResponse.error.message)
    }
  });
  }

  private seErrorNotification(notificationType: NotificationType, message: string) : void {
    if (message) {
      this.notificationService.notify(notificationType, message);
    } else {
      this.notificationService.notify(notificationType, 'An error occured. Please try again.');
    }
  }

  toggleShowPassword() {
    this.showPassword = !this.showPassword;
    this.inputType = this.showPassword ? 'text' : 'password'
  }

}


