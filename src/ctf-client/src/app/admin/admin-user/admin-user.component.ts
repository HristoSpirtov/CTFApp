import { Component, OnInit, TemplateRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { User } from 'src/app/shared/model/user';
import { UserService } from 'src/app/shared/service/user.service';
import { faAngry, faBan, faBolt, faBrain, faBug, faChartPie, faCode, faCrosshairs, faCrown, faEnvelope, faHatCowboy, faNetworkWired, faShieldAlt, faSkull, faTrashAlt, faTrophy, faUserEdit } from '@fortawesome/free-solid-svg-icons';
import { BsModalRef, BsModalService, ModalOptions } from 'ngx-bootstrap/modal';
import { SubmissionService } from 'src/app/shared/service/submission.service';
import { Solve } from 'src/app/shared/model/solve';
import { firstValueFrom, lastValueFrom } from 'rxjs';
import { ChallengeService } from 'src/app/shared/service/challenge.service';
import { Challenge } from 'src/app/shared/model/challenge';
import { PieChartData } from 'src/app/shared/model/pie-chart-data';
import { LineChartData } from 'src/app/shared/model/line-chart-model';
import { AwardService } from 'src/app/shared/service/award.service';
import { Award } from 'src/app/shared/model/award';
import { NotificationService } from 'src/app/shared/service/notification.service';
import { NotificationType } from 'src/app/shared/enum/notification-type.enum';

@Component({
  selector: 'app-admin-user',
  templateUrl: './admin-user.component.html',
  styleUrls: ['./admin-user.component.css']
})
export class AdminUserComponent implements OnInit {

  modalRef?: BsModalRef;
  userEdiIcon = faUserEdit;
  chartIcon = faChartPie;
  trophyIcon = faTrophy;
  mailIcon = faEnvelope;
  trashIcon = faTrashAlt;
  networkIcon = faNetworkWired;
  awardIcons : any[] = [
  {name : 'None', icon : null},
  {name : 'Shield', icon : faShieldAlt},
  {name : 'Bug', icon : faBug},
  {name : 'Crown', icon : faCrown},
  {name : 'Crosshairs', icon : faCrosshairs},
  {name : 'Ban', icon: faBan},
  {name : 'Lightning', icon : faBolt},
  {name : 'Skull', icon : faSkull},
  {name : 'Brain', icon : faBrain},
  {name : 'Code', icon: faCode},
  {name : 'Cowboy', icon : faHatCowboy},
  {name : 'Angry', icon : faAngry}
];

  user!: User;
  fails! : Solve[];
  solves! : Solve[];
  awards! : Award[];
  missingChallenges! : Challenge[];
  pieChartData : PieChartData[] = [];
  lineChartData : LineChartData[] = [];

  constructor(private notificationService : NotificationService, private awardService : AwardService, private challengeService : ChallengeService, private userService: UserService, private route: ActivatedRoute, private modalService: BsModalService, private submissionService: SubmissionService) { }

  ngOnInit(): void {
    this.getAsyncData();
  }

  async getAsyncData() {
    this.user = await firstValueFrom(this.userService.getUserByid(this.route.snapshot.paramMap.get('id') as string));
    this.fails = await lastValueFrom(this.submissionService.getFailsForUser(this.route.snapshot.paramMap.get('id') as string));
    this.solves = await lastValueFrom(this.submissionService.getSolvesForUser(this.route.snapshot.paramMap.get('id') as string));
    this.awards = await lastValueFrom(this.awardService.findAllAwardsByUserName(this.route.snapshot.paramMap.get('id') as string));
    this.missingChallenges = await lastValueFrom(this.challengeService.getMissingChallengesForUser(this.route.snapshot.paramMap.get('id') as string));
    this.lineChartData = await lastValueFrom(this.submissionService.getLineChartArgs(this.route.snapshot.paramMap.get('id') as string));
    this.pieChartData = [];
    this.pieChartData.push(new PieChartData("Fails", this.fails.length));
    this.pieChartData.push(new PieChartData("Solves", this.solves.length));
    
  }

  openStatisticsModal(template: TemplateRef<any>) {
    const config: ModalOptions = { class: 'modal-xl' };
    this.modalRef = this.modalService.show(template, config);
  }

  openAwardModal(template: TemplateRef<any>) {
    const config: ModalOptions = { class: 'modal-md' };
    this.modalRef = this.modalService.show(template, config);
  }
  
  // refactor to fetch only modified state!!!
  refresh() {
    this.ngOnInit();
  }

  onSubmitAward(form : Award) {
    form.userId = this.route.snapshot.paramMap.get('id') as string;
    this.awardService.awardUser(form).subscribe(response => {
      this.modalRef?.hide();
      this.notificationService.notify(NotificationType.SUCCESS, response.message);
      this.ngOnInit();
    });
    
  }
}

