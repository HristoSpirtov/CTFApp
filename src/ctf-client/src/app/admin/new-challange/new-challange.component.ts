import { NotificationType } from './../../shared/enum/notification-type.enum';
import { NotificationService } from './../../shared/service/notification.service';

import { Challenge } from './../../shared/model/challenge';
import { faEye, faQuestionCircle } from '@fortawesome/free-solid-svg-icons';
import { Component, OnInit } from '@angular/core';
import { ChallengeService } from 'src/app/shared/service/challenge.service';
import { Router } from '@angular/router';



@Component({
  selector: 'new-challange',
  templateUrl: './new-challange.component.html',
  styleUrls: ['./new-challange.component.css']
})
export class NewChallangeComponent implements OnInit {

  switchViewIcon = faEye;
  isSwitched = false;
  redirectToCheatSheet = faQuestionCircle;

  constructor(private challengeService : ChallengeService, private notificationService : NotificationService, private router : Router) { }

  ngOnInit(): void {
  }

  markdown : string = "";

  onKeyUp(value : string){
    this.markdown = value;
  }

  switchToMark() {
    this.isSwitched = !this.isSwitched;
    console.log(this.markdown);
  }

  onNavigate() {
    window.open("https://jfcere.github.io/ngx-markdown/cheat-sheet", "_blank");
  }

  onSubmit(challemgeForm : Challenge[]) {
    this.challengeService.createChallenge(challemgeForm).subscribe(response =>{
      this.notificationService.notify(NotificationType.SUCCESS, response.message)
      this.router.navigateByUrl('/admin/challenges')
    });
  }

  

  


}
