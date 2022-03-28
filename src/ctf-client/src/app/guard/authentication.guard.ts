import { NotificationService } from './../service/notification.service';
import { AuthenticationService } from './../service/authentication.service';
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
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

  constructor(private authenticationService : AuthenticationService, private router : Router, private notificationService : NotificationService) {}

  private isUserLoggedIn() : boolean {
    if (this.authenticationService.isUserLoggedIn()) {
      return true;
    }
    this.router.navigate(['/login']);
    this,this.notificationService.notify(NotificationType.ERROR, `You need to login to access this page`);
    return false;
  }
  
}
