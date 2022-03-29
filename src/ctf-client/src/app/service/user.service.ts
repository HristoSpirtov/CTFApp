import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http' 
import { Observable } from 'rxjs';
import { User } from '../model/user';

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


}
