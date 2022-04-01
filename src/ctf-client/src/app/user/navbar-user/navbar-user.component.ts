import { User } from '../../shared/model/user';
import { AuthenticationService } from '../../shared/service/authentication.service';
import { Component, OnInit } from '@angular/core';

import { faUserPlus, faSignInAlt, faBell, faUsers, faUserCircle, faWrench  } from '@fortawesome/free-solid-svg-icons';
import { faSignOutAlt } from '@fortawesome/free-solid-svg-icons'; 

@Component({
  selector: 'navbar-user',
  templateUrl: './navbar-user.component.html',
  styleUrls: ['./navbar-user.component.css']
})
export class NavbarUserComponent implements OnInit {

  isLoggedIn! : boolean;
  user! : User | null;


  signInIcon = faSignInAlt;
  registerIcon = faUserPlus;
  signoutIcon = faSignOutAlt;
  notificationIcon = faBell;
  teamIcon = faUsers;
  profileIcon = faUserCircle;
  adminIcon = faWrench;


  constructor(private authenticationService : AuthenticationService) {
  }

  ngOnInit(): void {
    this.authenticationService.isLoggedIn().subscribe(x => {
      this.isLoggedIn = x;
    });

    this.authenticationService.getUser().subscribe(x => {
      this.user = x;
    });
  }

  logout() {
    this.authenticationService.logout();
  }

  isAdmin() {
    return this.user?.roles.length == 2;
  }


}
