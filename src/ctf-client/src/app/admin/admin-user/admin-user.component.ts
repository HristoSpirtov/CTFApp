import { Component, OnDestroy, OnInit, TemplateRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { User } from 'src/app/shared/model/user';
import { UserService } from 'src/app/shared/service/user.service';
import { faChartPie, faEnvelope, faNetworkWired, faTrashAlt, faTrophy, faUserEdit } from '@fortawesome/free-solid-svg-icons';
import { BsModalRef, BsModalService, ModalOptions } from 'ngx-bootstrap/modal';
import { SubmissionService } from 'src/app/shared/service/submission.service';
import { Solve } from 'src/app/shared/model/solve';
import { firstValueFrom, lastValueFrom } from 'rxjs';
import { ChallengeService } from 'src/app/shared/service/challenge.service';
import { Challenge } from 'src/app/shared/model/challenge';
import { PieChartData } from 'src/app/shared/model/pie-chart-data';
import { LineChartData } from 'src/app/shared/model/line-chart-model';
import { EChartsOption } from 'echarts';

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

  user!: User;
  fails! : Solve[];
  solves! : Solve[];
  missingChallenges! : Challenge[];
  pieChartData : PieChartData[] = [];
  lineChartData : LineChartData[] = [];

  constructor(private challengeService : ChallengeService, private userService: UserService, private route: ActivatedRoute, private modalService: BsModalService, private submissionService: SubmissionService) { }

  ngOnInit(): void {
    this.getAsyncData();
    
  }

  async getAsyncData() {
    this.user = await firstValueFrom(this.userService.getUserByid(this.route.snapshot.paramMap.get('id') as string));
    this.fails = await lastValueFrom(this.submissionService.getFailsForUser(this.route.snapshot.paramMap.get('id') as string));
    this.solves = await lastValueFrom(this.submissionService.getSolvesForUser(this.route.snapshot.paramMap.get('id') as string));
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
  
  // refactor to fetch only modified state
  refresh() {
    this.ngOnInit();
  }
}

