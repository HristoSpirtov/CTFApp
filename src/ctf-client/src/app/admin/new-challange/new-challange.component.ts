import { NotificationType } from './../../shared/enum/notification-type.enum';
import { NotificationService } from './../../shared/service/notification.service';
import { Challenge } from './../../shared/model/challenge';
import { faEye, faQuestionCircle } from '@fortawesome/free-solid-svg-icons';
import { Component, OnDestroy, OnInit, TemplateRef } from '@angular/core';
import { ChallengeService } from 'src/app/shared/service/challenge.service';
import { Router } from '@angular/router';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { faUserPlus } from '@fortawesome/free-solid-svg-icons';
import { UserService } from 'src/app/shared/service/user.service';
import { User } from 'src/app/shared/model/user';
import { Subscription } from 'rxjs';




@Component({
  selector: 'new-challange',
  templateUrl: './new-challange.component.html',
  styleUrls: ['./new-challange.component.css']
})
export class NewChallangeComponent implements OnInit, OnDestroy {

  modalRef?: BsModalRef;
  userPlusIcon = faUserPlus;

  users! : User[];
  participatingUsers! : User[];
  notParticipatingUsers! : User[];
  subscription!: Subscription;



  switchViewIcon = faEye;
  isSwitched = false;
  redirectToCheatSheet = faQuestionCircle;
  markdown : string = "";

  constructor(private challengeService : ChallengeService, private notificationService : NotificationService, private router : Router, private  modalService : BsModalService, private userService : UserService) {
  }
  ngOnDestroy(): void {
    this.subscription.unsubscribe()
  }

  ngOnInit(): void {
    this.subscription = this.userService.getUsers().subscribe(users => this.users = users);
    
  }

  onKeyUp(value : string){
    this.markdown = value;
  }

  switchToMark() {
    this.isSwitched = !this.isSwitched;
  }

  onNavigate() {
    window.open("https://jfcere.github.io/ngx-markdown/cheat-sheet", "_blank");
  }

  onSubmit(challemgeForm : Challenge, challangeType : string) {
    challemgeForm.type = challangeType;
    challemgeForm.participants = this.participatingUsers;
    this.challengeService.createChallenge(challemgeForm).subscribe(response =>{
      this.notificationService.notify(NotificationType.SUCCESS, response.message)
      this.router.navigateByUrl('/admin/challenges')
    });
  }

  openParticipantsModal(template: TemplateRef<any>) {
    this.modalRef = this.modalService.show(template);
  }

  pushItmEventPart(event : User[]) {
    this.participatingUsers = event; 
    this.notParticipatingUsers = this.users.filter(u => !this.participatingUsers.includes(u));
  }

}
