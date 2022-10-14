import { Component, OnDestroy, OnInit, TemplateRef } from '@angular/core';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { faPencilAlt, faPlusCircle, faSort, faTrashAlt } from '@fortawesome/free-solid-svg-icons';
import { Subscription } from 'rxjs';
import { ChallengeService } from 'src/app/shared/service/challenge.service';
import { Challenge } from 'src/app/shared/model/challenge';
import { NotificationService } from 'src/app/shared/service/notification.service';
import { NotificationType } from 'src/app/shared/enum/notification-type.enum';
import { Router } from '@angular/router';

@Component({
  selector: 'admin-challenges',
  templateUrl: './admin-challenges.component.html',
  styleUrls: ['./admin-challenges.component.css']
})
export class AdminChallengesComponent implements OnInit, OnDestroy {

  //TODO: implement sorting and pagination

  addIcon = faPlusCircle;
  deleteIcon = faTrashAlt;
  editIcon = faPencilAlt;
  sortIcon = faSort;

  modalRef?: BsModalRef;

  isMasterSelected! : boolean;
  challenges : Challenge[];
  filteredChallenges! : Challenge[]
  subscription! : Subscription;
  selectedChallenges! : Challenge[];

  constructor(private modalService : BsModalService, private challengeService : ChallengeService, private notificationService : NotificationService, private router : Router ) {
    this.challenges = [];
   }

  ngOnInit(): void {
    this.selectedChallenges = [];
    this.isMasterSelected = false;
    this.subscription = this.challengeService.getAllChallenges().subscribe(challenges => {
      challenges.forEach( challenge => {
          this.challenges.push(challenge);
      })
      this.filteredChallenges = this.challenges;
    });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }


  filter(query : string, filterBy : string) {
    
    if(filterBy === 'name') {
      this.filteredChallenges = (query) ? this.challenges
      .filter(c => c.name.toLowerCase().includes(query.toLowerCase())) : this.challenges;
    } 
      
  }

  openDeleteModal(template: TemplateRef<any>) {
    this.modalRef = this.modalService.show(template);
  }

  openEditModal(template: TemplateRef<any>) {
    this.modalRef = this.modalService.show(template);
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

  deleteChallenges() {
    if(this.selectedChallenges.length > 0) {
      this.challengeService.deleteChallenges(this.selectedChallenges).subscribe(response => {
        
        this.modalRef?.hide();
        this.notificationService.notify(NotificationType.SUCCESS, response.message);
        this.selectedChallenges = [];
        //hack to refresh component
        this.router.navigateByUrl('/admin', { skipLocationChange: true }).then(() => {
          this.router.navigate(['/admin/challenges']);
        });
        
      });
      
    }
  }

  editChallenges(loginForm: Challenge) {
    let cloned : Challenge[] = [] ;
    this.selectedChallenges.forEach(u => cloned.push(Object.assign({}, u)));
    if(this.selectedChallenges.length > 0) {
      if( loginForm.value || loginForm.state != "") {
        cloned.forEach((challenge, index) => {
          //cloned[index].category =  loginForm.banned !="" ?  loginForm.category : challenge.category;
          cloned[index].value = loginForm.value ?  loginForm.value : challenge.value;
          cloned[index].state = loginForm.state !="" ?  loginForm.state : challenge.state;
        })
        this.challengeService.editChallenges(cloned).subscribe(response => {
          
          this.modalRef?.hide();
          this.notificationService.notify(NotificationType.SUCCESS, response.message);
          this.selectedChallenges = [];
          //hack to refresh component
          this.router.navigateByUrl('/admin', { skipLocationChange: true }).then(() => {
            this.router.navigate(['/admin/challenges']);
          });
        });
      }
    }
  }

}
