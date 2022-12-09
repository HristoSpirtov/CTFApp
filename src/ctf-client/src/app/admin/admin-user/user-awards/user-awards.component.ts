import { Component, EventEmitter, Input, OnInit, Output, TemplateRef } from '@angular/core';
import { faSort, faTrashAlt } from '@fortawesome/free-solid-svg-icons';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { NotificationType } from 'src/app/shared/enum/notification-type.enum';
import { Award } from 'src/app/shared/model/award';
import { iconsMap } from 'src/app/shared/model/award-icon';
import { AwardService } from 'src/app/shared/service/award.service';
import { NotificationService } from 'src/app/shared/service/notification.service';


@Component({
  selector: 'app-user-awards',
  templateUrl: './user-awards.component.html',
  styleUrls: ['./user-awards.component.css']
})
export class UserAwardsComponent implements OnInit {

  deleteIcon = faTrashAlt;
  sortIcon = faSort;
  modalRef?: BsModalRef;
  @Input() awards : Award[] = [];
  @Output() deleteAwardsEvent = new EventEmitter();
  isMasterSelected! : boolean;
  selectedAwards! : Award[];
  iconsMap = iconsMap

  constructor(private notificationService : NotificationService, private awardService : AwardService, private modalService : BsModalService) {
    this.awards = [];
    this.selectedAwards= [];
    
   }

  ngOnInit(): void {
  }

  radioChange(isCheked : boolean, award : Award) {
    if(isCheked) {
      this.selectedAwards.push(award);
    } else {
      this.selectedAwards.forEach((value,index) => {
        if(value.id == award.id) {
          this.selectedAwards.splice(index, 1);
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
      this.selectedAwards = [];
    if(this.isMasterSelected) {
      this.awards.forEach(ch => this.selectedAwards.push(ch));
    }
  }

  getIcon(icon : string) {
    return this.iconsMap[icon.toLocaleLowerCase()]
  }

  openDeleteModal(template: TemplateRef<any>) {
    this.modalRef = this.modalService.show(template);
  }

  deleteAwards() {
    if(this.selectedAwards.length > 0) {
      this.awardService.deleteAwards(this.selectedAwards).subscribe(response => {
        
        this.modalRef?.hide();
        this.notificationService.notify(NotificationType.SUCCESS, response.message);
        this.selectedAwards = [];
        
        this.deleteAwardsEvent.emit();
        
      });
    }
  }

}
