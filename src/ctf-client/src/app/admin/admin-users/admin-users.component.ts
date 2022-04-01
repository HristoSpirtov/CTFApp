import { HttpErrorResponse } from '@angular/common/http';
import { UserService } from './../../shared/service/user.service';
import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/shared/model/user';

@Component({
  selector: 'app-admin-users',
  templateUrl: './admin-users.component.html',
  styleUrls: ['./admin-users.component.css']
})
export class AdminUsersComponent implements OnInit {

  constructor(private userService : UserService) { }

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
