import { Component, OnInit, TemplateRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { Submission } from 'src/app/shared/model/submission';
import { SubmissionService } from 'src/app/shared/service/submission.service';
import { faSort, faTrashAlt } from '@fortawesome/free-solid-svg-icons';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { NotificationService } from 'src/app/shared/service/notification.service';
import { NotificationType } from 'src/app/shared/enum/notification-type.enum';

@Component({
  selector: 'app-submission',
  templateUrl: './submission.component.html',
  styleUrls: ['./submission.component.css']
})
export class SubmissionComponent implements OnInit {

  deleteIcon = faTrashAlt;
  sortIcon = faSort;

  type! : string;
  optionalChallengeId! : string;

  submissions : Submission[] = [];
  filteredSubmissions! : Submission[]
  isMasterSelected! : boolean;
  subscription! : Subscription;
  selectedSubmissions! : Submission[];

  modalRef?: BsModalRef;

  constructor(private notificationService : NotificationService, private modalService : BsModalService, private route : ActivatedRoute, private submissionService : SubmissionService) { }

  ngOnInit(): void {
    this.selectedSubmissions = [];
    this.isMasterSelected = false;
    this.type = this.route.snapshot.url[1]?.path;
    this.optionalChallengeId = this.route.snapshot.url[2]?.path;

    this.subscription = this.submissionService.getSubmissions(this.type, this.optionalChallengeId).subscribe(submissions => {
      this.submissions = submissions;
      this.filteredSubmissions = this.submissions;
    });
    
    
  }

  filter(query : string, filterBy : string) {
    
    if(filterBy === 'user') {
      this.filteredSubmissions = (query) ? this.submissions
      .filter(s => s.user.toLowerCase().includes(query.toLowerCase())) : this.submissions;
    } else if (filterBy === 'school') { 
      this.filteredSubmissions = (query) ? this.submissions
      .filter(s => s.school.toLowerCase().includes(query.toLowerCase())) : this.submissions;
    } else if (filterBy === 'challenge') { 
      this.filteredSubmissions = (query) ? this.submissions
      .filter(s => s.challenge.toLowerCase().includes(query.toLowerCase())) : this.submissions;
    } else if (filterBy === 'provided') { 
      this.filteredSubmissions = (query) ? this.submissions
      .filter(s => s.provided.toLowerCase().includes(query.toLowerCase())) : this.submissions;
    }
      
  }

  radioChange(isCheked : boolean, submission : Submission) {
    if(isCheked) {
      this.selectedSubmissions.push(submission);
    } else {
      this.selectedSubmissions.forEach((value,index) => {
        if(value.id == submission.id) {
          this.selectedSubmissions.splice(index, 1);
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
      this.selectedSubmissions = [];
    if(this.isMasterSelected) {
      this.submissions.forEach(s => this.selectedSubmissions.push(s));
      
    } 
  }

  openDeleteModal(template: TemplateRef<any>) {
    this.modalRef = this.modalService.show(template);
  }

  deleteSubmissions() {
    if(this.selectedSubmissions.length != 0) {
      this.submissionService.deleteSubmissions(this.selectedSubmissions).subscribe(response =>{
        this.modalRef?.hide();
        this.notificationService.notify(NotificationType.SUCCESS, response.message);
        this.selectedSubmissions = [];
        this.ngOnInit();
      });
    }
    
  }
}
