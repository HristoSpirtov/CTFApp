import { Component, OnInit } from '@angular/core';

import { faUserPlus, faSignInAlt } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'navbar-user',
  templateUrl: './navbar-user.component.html',
  styleUrls: ['./navbar-user.component.css']
})
export class NavbarUserComponent implements OnInit {

  signInIcon = faSignInAlt;
  registerIcon = faUserPlus;


  constructor() { }

  ngOnInit(): void {
  }

}
