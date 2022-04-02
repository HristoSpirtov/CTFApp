import { HttpResponse } from '@angular/common/http';
import { NotificationType } from './../../shared/enum/notification-type.enum';
import { NotificationService } from './../../shared/service/notification.service';
import { Observable } from 'rxjs';
import { faPencilAlt, faPlusCircle, faSearch, faTrashAlt } from '@fortawesome/free-solid-svg-icons';
import { UserService } from './../../shared/service/user.service';
import { Component, OnInit, TemplateRef } from '@angular/core';
import { User } from 'src/app/shared/model/user';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';

@Component({
  selector: 'app-admin-users',
  templateUrl: './admin-users.component.html',
  styleUrls: ['./admin-users.component.css']
})
export class AdminUsersComponent implements OnInit {

  modalRef?: BsModalRef;

  addIcon = faPlusCircle;
  searchIcon = faSearch;
  deleteIcon = faTrashAlt;
  editIcon = faPencilAlt;

  users$! : Observable<User[]>
  selectedUsers! : User[];



  constructor(private userService : UserService, private modalService: BsModalService, private notificationService : NotificationService) {
    
  }

  openModal(template: TemplateRef<any>) {
    this.modalRef = this.modalService.show(template);
  }

  ngOnInit(): void {
    this.selectedUsers = [];
    this.users$ = this.userService.getUsers();

  }

  radioChange(isCheked : boolean, user : User) {
    if(isCheked) {
      this.selectedUsers.push(user)
    } else {
      this.selectedUsers.forEach((value,index) => {
        if(value.id == user.id) {
          this.selectedUsers.splice(index, 1)
        }
      });
    }
    this.selectedUsers.forEach(x => console.log(x));
  }

  deleteUsers() {
    if(this.selectedUsers.length > 0) {
      this.userService.deleteUsers(this.selectedUsers).subscribe(response => {
        this.users$ = this.userService.getUsers();
        this.modalRef?.hide();
        this.notificationService.notify(NotificationType.SUCCESS, response.message);
      });
    }
  }




}
