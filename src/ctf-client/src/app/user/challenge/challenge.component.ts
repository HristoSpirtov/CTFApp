import { Subscription } from 'rxjs';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
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
  
  constructor(private challengeService : ChallengeService, public modalService: BsModalService) { 
    this.challenges = [];
  }
  
  ngOnInit(): void {
    this.subscription = this.challengeService.getChallenges().subscribe(challenges => {
      this.challenges = this.splitArr(challenges, 4);
      console.log(this.challenges);
    })
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  
 openChallengeModal(template: TemplateRef<any>, challenge : Challenge) {
    this.clickedChallange = challenge;
    this.modalRef = this.modalService.show(template)
      
 }

 private splitArr(arr : any, size : any) {
  let newArr = [];
  for(let i = 0; i< arr.length; i += size) {
    newArr.push(arr.slice(i, i+size));
  }
  return newArr;
}

}
