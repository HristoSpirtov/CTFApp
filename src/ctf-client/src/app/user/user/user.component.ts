import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  adminString! : String;
  
  constructor(private router : Router) {}

  ngOnInit(): void {
    this.router.events.subscribe(() => {
      this.adminString = this.router.url.split("/")[1];
    });
  }

}
