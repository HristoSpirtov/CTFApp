import { NotificationType } from './../enum/notification-type.enum';
import { HttpErrorResponse } from '@angular/common/http';
import { User } from './../model/user';
import { Router } from '@angular/router';
import { NotificationService } from './../service/notification.service';
import { AuthenticationService } from './../service/authentication.service';
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
  

  constructor(private router : Router, private authenticationService : AuthenticationService, private notificationService : NotificationService) { }

  ngOnInit(): void {
    this.showPassword = false;
    this.inputType = "password";
  }

  onRegister(registerForm : any) {
    this.authenticationService.register(registerForm).subscribe({
      next: (response : User) => {
        this.seErrorNotification(NotificationType.SUCCESS, `A new user was created with username ${response.username}`)
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
      console.log("sdasdas")
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


