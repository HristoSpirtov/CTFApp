import { Component, OnInit, TemplateRef } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { faTimes, faEdit } from '@fortawesome/free-solid-svg-icons';
import { BsModalRef, BsModalService, ModalOptions } from 'ngx-bootstrap/modal';
import { Observable } from 'rxjs';
import { NotificationType } from 'src/app/shared/enum/notification-type.enum';
import { Flag } from 'src/app/shared/model/flag';
import { ChallengeService } from 'src/app/shared/service/challenge.service';
import { NotificationService } from 'src/app/shared/service/notification.service';

@Component({
  selector: 'app-admin-flag',
  templateUrl: './admin-flag.component.html',
  styleUrls: ['./admin-flag.component.css']
})
export class AdminFlagComponent implements OnInit {

  deleteIcon = faTimes;
  editIcon = faEdit;
  modalRef?: BsModalRef;

  constructor(private router: Router, private notificationService: NotificationService, private route: ActivatedRoute, private challengeService: ChallengeService, public modalService: BsModalService) { }

  flags!: Observable<Flag[]>

  ngOnInit(): void {
    this.flags = this.challengeService.findAllFlagsByChallengeId(this.route.snapshot.paramMap.get('id') as string);
  }

  openDeleteFlagModal(template: TemplateRef<any>, flag: Flag) {
    const data = {
      data1: flag.id
    }
    this.modalRef = this.modalService.show(template, {
      initialState: data
    });
  }

  openEditFlagModal(template: TemplateRef<any>, flag: Flag) {
    const data = {
      data1: flag.flag,
      data2: flag.id
    }
    this.modalRef = this.modalService.show(template, {
      initialState: data,
      class: 'modal-lg'
    });
  }

  openCreateFlagModal(template: TemplateRef<any>) {
    const config: ModalOptions = { class: 'modal-lg' };
    this.modalRef = this.modalService.show(template, config);
  }

  deleteFlag(id: any) {
    this.challengeService.deleteFlag(id as string).subscribe(response => {
      this.modalRef?.hide();
      this.notificationService.notify(NotificationType.SUCCESS, response.message);
      this.ngOnInit();
    });
  }

  createFlag(createFlagForm: any) {
    this.challengeService.createFlag(createFlagForm, this.route.snapshot.paramMap.get('id') as string).subscribe(response => {
      this.modalRef?.hide();
      this.notificationService.notify(NotificationType.SUCCESS, response.message);
      this.ngOnInit();
    })
  }

  updateFlag(updateFlagForm: any, id : any) {
    const updateFlag = {
      flag : updateFlagForm.flag,
      id: id
    }
    
    this.challengeService.editFlag(updateFlag).subscribe(response => {
      this.modalRef?.hide();
      this.notificationService.notify(NotificationType.SUCCESS, response.message);
      this.ngOnInit();
    });
  }
}
