import { Component, OnInit } from '@angular/core';
import { faSort, faTrashAlt } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-user-awards',
  templateUrl: './user-awards.component.html',
  styleUrls: ['./user-awards.component.css']
})
export class UserAwardsComponent implements OnInit {

  deleteIcon = faTrashAlt;
  sortIcon = faSort;

  constructor() { }

  ngOnInit(): void {
  }

}
