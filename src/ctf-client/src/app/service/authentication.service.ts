import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http' 
import { BehaviorSubject, Observable } from 'rxjs';
import { User } from '../model/user';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private _host;
  private token: string;
  private loggedInUser!: string;
  private jwtHelper;
  isLoginSubject = new BehaviorSubject<boolean>(this.isUserLoggedIn());
  

  constructor(private http: HttpClient) {
    this._host = environment.apiUrl;
    this.token = "";
    this.loggedInUser = "";
    this.jwtHelper = new JwtHelperService();
   }

  public login(loginForm: any) : Observable<HttpResponse<User>> {
    this.http.post<User>(`${this.host}/api/login`, loginForm, {observe: 'response'}).subscribe(() => {
      this.isLoginSubject.next(true);
    })
    return this.http.post<User>(`${this.host}/api/login`, loginForm, {observe: 'response'});
  }

  public isLoggedIn() : Observable<boolean> {
    return this.isLoginSubject.asObservable();
}

  

  public register(registerForm: any) : Observable<User> {
    return this.http.post<User>(`${this.host}/api/register`, registerForm);
  }

  public logout() : void {
    this.token = "";
    this.loggedInUser = "";
    localStorage.removeItem('user');
    localStorage.removeItem('token');
    localStorage.removeItem('users');
    this.isLoginSubject.next(false);
  }

  public saveToken(token: string) : void {
    this.token = token;
    localStorage.setItem('token', token);
  }

  public addUserToCash(user: User) : void {
    localStorage.setItem('user', JSON.stringify(user));
  }

  public getUserFromCache() : User {
    return JSON.parse(localStorage.getItem('user')!)
  }

  public loadToken() : void {
    this.token = localStorage.getItem('token') as string;
  }

  public getToken() : string {
    return this.token;
  }

  public isUserLoggedIn() : boolean {
    this.loadToken();
    if (this.token !== null && this.token !== undefined) {   
      console.log(this.token);
      if (this.jwtHelper.decodeToken(this.token).sub != null || '') {
        if (!this.jwtHelper.isTokenExpired(this.token)) {
          this.loggedInUser = this.jwtHelper.decodeToken(this.token).sub;
          return true;
        }
      }
    } 
    return false;

  }

  get host() : string {
    return this._host;
  }
}
