import { Router } from '@angular/router';
import { NotificationType } from './../../shared/enum/notification-type.enum';
import { NotificationService } from './../../shared/service/notification.service';
import { AuthenticationService } from './../../shared/service/authentication.service';
import { User } from './../../shared/model/user';
import { SubmissionService } from './../../shared/service/submission.service';
import { Submission } from './../../shared/model/submission';
import { Subscription, take } from 'rxjs';
import { BsModalRef, BsModalService, ModalOptions } from 'ngx-bootstrap/modal';
import { ChallengeService } from 'src/app/shared/service/challenge.service';
import { Challenge } from './../../shared/model/challenge';
import { Component, OnInit, OnDestroy, TemplateRef } from '@angular/core';


@Component({
  selector: 'app-home',
  templateUrl: './challenge.component.html',
  styleUrls: ['./challenge.component.css']
})
export class ChallengeComponent implements OnInit, OnDestroy {

  challenges! : Challenge[][];  
  modalRef?: BsModalRef;
  clickedChallange! : Challenge 
  subscription! : Subscription
  user! : User
  solves!: Submission[];
  curSolves! : Submission[];

  
  constructor(private challengeService : ChallengeService, public modalService: BsModalService, private router : Router,
    private submissionService : SubmissionService, private authenticationService : AuthenticationService, private notificationService : NotificationService) { 
    this.challenges = [];
    this.authenticationService.userSubject.pipe(take(1)).subscribe(user => {
      this.user = user!;
    })
  }
  
  ngOnInit(): void {
    this.subscription = this.challengeService.getChallenges().subscribe(challenges => {
      this.challenges = this.splitArr(challenges, 4);
      this.solves = this.challenges
        .flatMap(ch => ch)
        .map(ch => ch.submissions)
        .flatMap(sub => sub)
        .filter(sub => sub.type === 'CORRECT')
    })
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  
 openChallengeModal(template: TemplateRef<any>, challenge : Challenge) {
    this.clickedChallange = challenge;
    this.curSolves = this.solves.filter(sub => sub.challenge === this.clickedChallange.name);
  
    this.modalRef = this.modalService.show(template, {
      class: 'modal-lg'
    });
      
 }

 submit(formData : string) {
   let submission : Submission = new Submission;
   submission.challenge  = this.clickedChallange.name;
   submission.provided = formData;
   this.submissionService.createSubmission(submission).subscribe(submission => {
    this.modalRef?.hide();
     if(submission.type == 'CORRECT') {
      this.notificationService.notify(NotificationType.SUCCESS, 'CORRECT');
     } else {
      this.notificationService.notify(NotificationType.ERROR, 'INCORRECT')
     }
     //hack to refresh component
     this.router.navigateByUrl('/scoreboard', { skipLocationChange: true }).then(() => {
      this.router.navigate(['/challenges']);
    });
   });

 }

isSolvedByUser(challenge : Challenge) : boolean {
  let arr = challenge.submissions.filter(submission => submission.user == this.user.username);
  return arr.filter(submission => submission.type == 'CORRECT').length == 1 ? true : false;
}

 private splitArr(arr : any, size : any) {
    let newArr = [];
    for(let i = 0; i< arr.length; i += size) {
      newArr.push(arr.slice(i, i+size));
    }
    return newArr;
  }

}
