import { User } from '../model/user';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http' 
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  
  private host;
  
  constructor(private http: HttpClient, ) {
    this.host = environment.apiUrl;
   }

  public getUsers() : Observable<User[]> {
    return this.http.get<User[]>(`${this.host}/api/users`)
  }

  public deleteUsers(selectedUsers : User[]) : Observable<any> {
    return this.http.post<any>(`${this.host}/api/users/delete`, selectedUsers)
  }

  public editUsers(selectedUsers : User[]) : Observable<any> {
    return this.http.patch<any>(`${this.host}/api/users/edit`, selectedUsers);
  }

  public getUserByid(id : string): Observable<User> {
    return this.http.get<User>(`${this.host}/api/users/${id}`)
  }


}
