import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { User } from '../model/user';



@Component({
  selector: 'app-sortable',
  templateUrl: './sortable.component.html',
  styleUrls: ['./sortable.component.css']
})
export class SortableComponent {
  @Input() notParticipatingUsers! : User[];
  @Input() participatingUsers! : User[];
  @Output() itemPushEventPart = new EventEmitter<User[]>();
  
  
  constructor() {}

  
  
  changePart() {
    this.itemPushEventPart.emit(this.participatingUsers);     
  }
}
