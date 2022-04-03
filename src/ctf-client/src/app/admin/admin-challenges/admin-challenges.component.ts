import { faPlusCircle } from '@fortawesome/free-solid-svg-icons';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'admin-challenges',
  templateUrl: './admin-challenges.component.html',
  styleUrls: ['./admin-challenges.component.css']
})
export class AdminChallengesComponent implements OnInit {

  addIcon = faPlusCircle;

  constructor() { }

  ngOnInit(): void {
  }

}
