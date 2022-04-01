import { User } from '../model/user';
import { NotificationType } from '../enum/notification-type.enum';
import { NotificationService } from '../service/notification.service';
import { AuthenticationService } from '../service/authentication.service';
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, CanActivateChild, Router } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminGuard implements CanActivateChild {
  canActivateChild(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return this.isAdmin();
  }
  
  user! : User | null;

  constructor(private authenticationService : AuthenticationService, private router : Router, private notificationService : NotificationService) {
   this.authenticationService.getUser().subscribe(x => {
     this.user = x;
   })

  
  }


  private isAdmin(): boolean {  
    if (this.user?.roles.length == 2) {    
      return true;
    } 
    this.router.navigateByUrl('/challenges');
    this.notificationService.notify(NotificationType.ERROR, `You need to login with admin account`);
    return false;
  }

}
