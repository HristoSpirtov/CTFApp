import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'jumbotron',
  templateUrl: './jumbotron.component.html',
  styleUrls: ['./jumbotron.component.css'],
  host: {'class': 'admin'}
})
export class JumbotronComponent implements OnInit {



  constructor() { }

  ngOnInit(): void {
  }

}
