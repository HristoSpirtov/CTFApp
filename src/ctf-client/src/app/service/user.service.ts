import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http' 
import { Observable } from 'rxjs';
import { User } from '../model/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private host;
  
  constructor(private http: HttpClient) {
    this.host = environment.apiUrl;
   }

  public getUsers() : Observable<User[] | HttpErrorResponse> {
    return this.http.get<User[]>(`${this.host}/api/users`)
  }

  public addUsersToCache(users: User[]) : void {
    localStorage.setItem('users', JSON.stringify(users));
  }

  public getUsersFromCache() : User[] | null {
    if (localStorage.getItem('users')) {
      return JSON.parse(localStorage.getItem('users')!);
    }
    return null;
  }

  


   
}
