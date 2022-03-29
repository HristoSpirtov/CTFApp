import { HttpErrorResponse } from '@angular/common/http';
import { User } from './../model/user';
import { UserService } from './../service/user.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './challenge.component.html',
  styleUrls: ['./challenge.component.css']
})
export class ChallengeComponent implements OnInit {

  users!: User[];

  constructor(private userService : UserService) { 
  
  }

  

  ngOnInit(): void {
    this.userService.getUsers().subscribe({
      next: (users : User[]) => {
        console.log(users)
    },     
      error: (errorResponse: HttpErrorResponse) => {
        console.log(errorResponse);
    }
  });
  }

}
