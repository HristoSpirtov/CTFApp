import { AuthenticationService } from './../service/authentication.service';
import { Component, OnInit } from '@angular/core';

import { faUserPlus, faSignInAlt  } from '@fortawesome/free-solid-svg-icons';
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


  constructor(private authenticationService : AuthenticationService) {
    
   }

  ngOnInit(): void {
    this.authenticationService.isLoggedIn().subscribe(x => {
      this.isLoggedIn = x;
    });
    
  }

  logout() {
    console.log("logout")
    this.authenticationService.logout();
  }

}
