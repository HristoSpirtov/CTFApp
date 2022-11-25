import { Router } from '@angular/router';
import { NotificationType } from './../../shared/enum/notification-type.enum';
import { NotificationService } from './../../shared/service/notification.service';
import { Subscription } from 'rxjs';
import { faPencilAlt, faPlusCircle, faSort, faTrashAlt } from '@fortawesome/free-solid-svg-icons';
import { UserService } from './../../shared/service/user.service';
import { Component, OnInit, TemplateRef, OnDestroy, HostBinding } from '@angular/core';
import { User } from 'src/app/shared/model/user';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { EChartsOption } from 'echarts';

@Component({
  selector: 'app-admin-users',
  templateUrl: './admin-users.component.html',
  styleUrls: ['./admin-users.component.css']
})
export class AdminUsersComponent implements OnInit, OnDestroy {

  //TODO: implement sorting and pagination

  modalRef?: BsModalRef;

  addIcon = faPlusCircle;
  deleteIcon = faTrashAlt;
  editIcon = faPencilAlt;
  sortIcon = faSort;

  isMasterSelected! : boolean;
  users : User[]
  filteredUsers! : User[]
  subscription! : Subscription;
  selectedUsers! : User[];



  constructor(private userService : UserService, private modalService: BsModalService, private notificationService : NotificationService, private router : Router) {
    this.users = [];
    
  }
  

  filter(query : string, filterBy : string) {
    if(filterBy == "username") {
      this.filteredUsers = (query) ? this.users.filter(u => u.username.toLowerCase().includes(query.toLowerCase())) : this.users;
    } else {
      this.filteredUsers = (query) ? this.users.filter(u => u.school.toLowerCase().includes(query.toLowerCase())) : this.users;
    }
  }

  openDeleteModal(template: TemplateRef<any>) {
    this.modalRef = this.modalService.show(template);
  }

  openEditModal(template: TemplateRef<any>) {
    this.modalRef = this.modalService.show(template);
  }

  ngOnInit(): void {
    this.isMasterSelected = false;
    this.selectedUsers = [];
    this.subscription = this.userService.getUsers().subscribe(users => {
      users.forEach( user => {
          this.users.push(user);
      })
      this.filteredUsers = this.users;
    });

  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  

  deleteUsers() {
    if(this.selectedUsers.length > 0) {
      this.userService.deleteUsers(this.selectedUsers).subscribe(response => {
        // this.userService.getUsers().subscribe(users => this.users = users);
        this.modalRef?.hide();
        this.notificationService.notify(NotificationType.SUCCESS, response.message);
        this.selectedUsers = [];
        //hack to refresh component
        this.router.navigateByUrl('/admin', { skipLocationChange: true }).then(() => {
          this.router.navigate(['/admin/users']);
        });
        
      });
      
    }
  }

  editUsers(loginForm: any) {
    let cloned : User[] = [] ;
    this.selectedUsers.forEach(u => cloned.push(Object.assign({}, u)));
    if(this.selectedUsers.length > 0) {
      if(loginForm.verified != "" || loginForm.hidden != "" || loginForm.banned != "") {
        cloned.forEach((user, index) => {
          cloned[index].banned =  loginForm.banned !="" ?  loginForm.banned : user.banned;
          cloned[index].hidden = loginForm.hidden !="" ?  loginForm.hidden : user.hidden;
        })
        this.userService.editUsers(cloned).subscribe(response => {
          
          this.modalRef?.hide();
          this.notificationService.notify(NotificationType.SUCCESS, response.message);
          this.selectedUsers = [];
          //hack to refresh component
          this.router.navigateByUrl('/admin', { skipLocationChange: true }).then(() => {
            this.router.navigate(['/admin/users']);
          });
        });
      }
    }
  }

  radioChange(isCheked : boolean, user : User) {
    if(isCheked && user.roles.length !== 2) {
      this.selectedUsers.push(user);
    } else {
      this.selectedUsers.forEach((value,index) => {
        if(value.id == user.id) {
          this.selectedUsers.splice(index, 1);
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
      this.selectedUsers = [];
    if(this.isMasterSelected) {
      this.users.forEach(u => {
        if(u.roles.length === 1) {
          this.selectedUsers.push(u);
        }
        
      });
      
    } 
  }
}
