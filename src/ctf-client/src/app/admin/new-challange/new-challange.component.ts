import { faEye } from '@fortawesome/free-solid-svg-icons';
import { Component, OnInit } from '@angular/core';



@Component({
  selector: 'new-challange',
  templateUrl: './new-challange.component.html',
  styleUrls: ['./new-challange.component.css']
})
export class NewChallangeComponent implements OnInit {

  switchViewIcon = faEye;
  isSwitched = false;

  constructor() { }

  ngOnInit(): void {
  }

  markdown : string = "";

  onKeyUp(value : string){
    this.markdown = value;
  }

  switchToMark() {
    this.isSwitched = !this.isSwitched;
  }


}
