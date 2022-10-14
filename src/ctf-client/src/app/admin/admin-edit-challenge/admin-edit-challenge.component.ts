import { Component, OnInit, TemplateRef } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Challenge } from 'src/app/shared/model/challenge';
import { ChallengeService } from 'src/app/shared/service/challenge.service';
import { faFileAlt, faTasks, faComments, faTrashAlt, faEye, faQuestionCircle, faUserPlus } from '@fortawesome/free-solid-svg-icons';
import { BsModalRef, BsModalService, ModalOptions } from 'ngx-bootstrap/modal';
import { User } from 'src/app/shared/model/user';
import { UserService } from 'src/app/shared/service/user.service';
import { forkJoin } from 'rxjs';
import { NotificationService } from 'src/app/shared/service/notification.service';
import { NotificationType } from 'src/app/shared/enum/notification-type.enum';

@Component({
  selector: 'app-admin-edit-challenge',
  templateUrl: './admin-edit-challenge.component.html',
  styleUrls: ['./admin-edit-challenge.component.css']
})
export class AdminEditChallengeComponent implements OnInit {

  challenge! : Challenge
  fileAltIcon = faFileAlt;
  tasktsIcon = faTasks;
  commentsIcon = faComments;
  trashIcon = faTrashAlt;
  switchViewIcon = faEye;
  redirectToCheatSheet = faQuestionCircle;
  userPlusIcon = faUserPlus;
  modalRef?: BsModalRef;

  users! : User[];
  participatingUsers: User[] = [];
  notParticipatingUsers : User[] = [];
  isSwitched = false;
  markdown : string = "";

  constructor(private notificationService : NotificationService, private router : Router, private challengeService : ChallengeService, private route : ActivatedRoute, public  modalService : BsModalService, private userService : UserService) { }

  ngOnInit(): void {
    let tasks$ = [];
    tasks$.push(this.challengeService.findChallengeById(this.route.snapshot.paramMap.get('id') as string));
    tasks$.push(this.userService.getUsers());
    forkJoin(tasks$).subscribe(results => {
      this.challenge = results[0] as Challenge;
      this.markdown = this.challenge.description;
      this.users = results[1] as User[];  
      this.participatingUsers = this.users.filter(u => {
        return this.challenge.participants.some(p => {
          return u.id === p.id;
        });
      });
      
      this.notParticipatingUsers = this.users.filter(u => {
        return !this.challenge.participants.some(p => {
          return u.id === p.id;
        });
      });  
      console.log(this.challenge);    
    }) 
    
    
  }

  switchToMark() {
    this.isSwitched = !this.isSwitched;
  }

  onNavigate() {
    window.open("https://jfcere.github.io/ngx-markdown/cheat-sheet", "_blank");
  }

  openParticipantsModal(template: TemplateRef<any>) {
    this.modalRef = this.modalService.show(template);
  }

  pushItmEventPart(event : User[]) {
    
    this.participatingUsers = event; 
    this.notParticipatingUsers = this.users.filter(u => !this.participatingUsers.includes(u));

  }

  onSubmit(challengeEditForm : Challenge) {
    challengeEditForm.participants = this.participatingUsers;
    this.challengeService.editChallenge(challengeEditForm, (this.route.snapshot.paramMap.get('id') as string)).subscribe(ch => {

      this.notificationService.notify(NotificationType.SUCCESS, "Challenge was successfully updated");
      this.ngOnInit();
    });
  }

  openDeleteChallengeModal(template: TemplateRef<any>) {
    const data = {
      name : this.challenge.name
    }
    this.modalRef = this.modalService.show(template, {
      initialState: data
    });
  }

  deleteChallenge() {
    this.challengeService.deleteChallenges([this.challenge]).subscribe(response => {
      this.modalRef?.hide();
      this.notificationService.notify(NotificationType.SUCCESS, response.message);
      this.router.navigateByUrl('/admin/challenges');  
    })
  }

  redirect(id : string) {
    this.router.navigateByUrl(`/admin/submissions/correct/${id}`);
  }

  openPreviewModal(template: TemplateRef<any>) {
    const config: ModalOptions = { class: 'modal-lg' };
    this.modalRef = this.modalService.show(template, config);
  }
}
