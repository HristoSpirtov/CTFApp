import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { AuthenticationService } from '../../shared/service/authentication.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-index',
  templateUrl: './intex.component.html',
  styleUrls: ['./intex.component.css']
})
export class IndexComponent implements OnInit {

  isLoggedIn!: boolean;
  subscription : Subscription;

  constructor(private authenticationService : AuthenticationService, private router : Router) {
    this.subscription = this.authenticationService.isLoggedIn().subscribe(x => {
      this.isLoggedIn = x;
    })
   }

  ngOnInit(): void {
    if (this.isLoggedIn) {
      this.router.navigateByUrl('/challenges')
    } 
  }

}
