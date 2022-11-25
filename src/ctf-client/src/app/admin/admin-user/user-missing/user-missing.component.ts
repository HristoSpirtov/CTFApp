import { Component, EventEmitter, Input, OnInit, Output, TemplateRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { faCheck, faSort } from '@fortawesome/free-solid-svg-icons';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { NotificationType } from 'src/app/shared/enum/notification-type.enum';
import { Challenge } from 'src/app/shared/model/challenge';
import { NotificationService } from 'src/app/shared/service/notification.service';
import { SubmissionService } from 'src/app/shared/service/submission.service';

@Component({
  selector: 'app-user-missing',
  templateUrl: './user-missing.component.html',
  styleUrls: ['./user-missing.component.css']
})
export class UserMissingComponent implements OnInit {

  @Input() username!: string;
  @Output() deleteSolvesEvent = new EventEmitter();
  modalRef?: BsModalRef;

  solveIcon = faCheck;
  sortIcon = faSort;

  @Input() challenges : Challenge[];
  isMasterSelected! : boolean;
  selectedChallenges! : Challenge[];

  constructor(private notificationService : NotificationService, private route : ActivatedRoute, private modalService : BsModalService, private submissionService : SubmissionService) {
    this.challenges = [];
    this.selectedChallenges= [];
  }

  ngOnInit(): void {  
    
  }

  radioChange(isCheked : boolean, challenge : Challenge) {
    if(isCheked) {
      this.selectedChallenges.push(challenge);
    } else {
      this.selectedChallenges.forEach((value,index) => {
        if(value.id == challenge.id) {
          this.selectedChallenges.splice(index, 1);
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
      this.selectedChallenges = [];
    if(this.isMasterSelected) {
      this.challenges.forEach(ch => this.selectedChallenges.push(ch));
    } 
  }

  openSolveModal(template: TemplateRef<any>) {
    this.modalRef = this.modalService.show(template);
  }

  solveChallenges() {
    
    if(this.selectedChallenges.length > 0) {
      this.submissionService.solveChallengesByAdmin(this.selectedChallenges, this.route.snapshot.paramMap.get('id') as string).subscribe(response => {
        
        this.modalRef?.hide();
        this.notificationService.notify(NotificationType.SUCCESS, response.message);
        this.challenges = [];
        
        this.deleteSolvesEvent.emit();
      }); 
    }
  }

}
