import { Component, EventEmitter, Input, OnInit, Output, TemplateRef } from '@angular/core';
import { faSort, faTrashAlt } from '@fortawesome/free-solid-svg-icons';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { NotificationType } from 'src/app/shared/enum/notification-type.enum';
import { Solve } from 'src/app/shared/model/solve';
import { NotificationService } from 'src/app/shared/service/notification.service';
import { SubmissionService } from 'src/app/shared/service/submission.service';

@Component({
  selector: 'app-user-fails',
  templateUrl: './user-fails.component.html',
  styleUrls: ['./user-fails.component.css']
})
export class UserFailsComponent implements OnInit {

  deleteIcon = faTrashAlt;
  sortIcon = faSort;
  modalRef?: BsModalRef; 

  @Input() solves : Solve[] = [];
  @Output() deleteSolvesEvent = new EventEmitter();
  isMasterSelected! : boolean;
  selectedSolves! : Solve[];

  constructor(private notificationService : NotificationService, private submissionService : SubmissionService, private modalService : BsModalService) {
    this.solves = [];
    this.selectedSolves=[];
  }

  ngOnInit(): void {}

  radioChange(isCheked : boolean, solve : Solve) {
    if(isCheked) {
      this.selectedSolves.push(solve);
    } else {
      this.selectedSolves.forEach((value,index) => {
        if(value.id == solve.id) {
          this.selectedSolves.splice(index, 1);
        }
      });
    }
  }

  radioChangeMaster() {
    this.isMasterSelected = !this.isMasterSelected;
    let checkboxes = document.getElementsByName('chk');
      checkboxes.forEach(ch => {
        let current = ch as HTMLInputElement;
        current.checked = this.isMasterSelected; 
      })
      this.selectedSolves = [];
    if(this.isMasterSelected) {
      this.solves.forEach(ch => this.selectedSolves.push(ch));
    } 
    
  }


  openDeleteModal(template: TemplateRef<any>) {
    this.modalRef = this.modalService.show(template);
  }

  deleteSolves() {
    if(this.selectedSolves.length > 0) {
      this.submissionService.deleteSubmissions(this.selectedSolves).subscribe(response => {
        
        this.modalRef?.hide();
        this.notificationService.notify(NotificationType.SUCCESS, response.message);
        this.selectedSolves = [];
        
        this.deleteSolvesEvent.emit();
        
      });
      
    }
  }
}
