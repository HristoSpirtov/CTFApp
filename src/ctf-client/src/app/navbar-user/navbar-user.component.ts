import { AuthenticationService } from './../service/authentication.service';
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
    
  }

  logout() {
    this.authenticationService.logout();
  }

}
