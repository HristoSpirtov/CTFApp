import { NotificationService } from './../service/notification.service';
import { AuthenticationService } from './../service/authentication.service';
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree, CanActivateChild } from '@angular/router';
import { Observable } from 'rxjs';
import { NotificationType } from '../enum/notification-type.enum';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationGuard implements CanActivate {
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return this.isUserLoggedIn();
  }

  isLoggedIn! : boolean;

  constructor(private authenticationService : AuthenticationService, private router : Router, private notificationService : NotificationService) {
    this.authenticationService.isLoggedIn().subscribe(x => {
      this.isLoggedIn = x;
    });
  }
  canActivateChild(): boolean {
    return this.isUserLoggedIn();
  }

  private isUserLoggedIn() : boolean {
    if (this.isLoggedIn) {
      return true;
    }
    this.router.navigateByUrl('/login');
    this.notificationService.notify(NotificationType.ERROR, `You need to login to access this page`);
    return false;
  }

  
}
