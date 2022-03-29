import { HeaderType } from './../enum/header-type.enum';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpResponse } from '@angular/common/http' 
import { BehaviorSubject, map, Observable, pipe } from 'rxjs';
import { User } from '../model/user';
import { JwtHelperService } from '@auth0/angular-jwt';


@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private _host;
  private jwtHelper;
  private isLoginSubject;
  

  constructor(private http: HttpClient) {
    this._host = environment.apiUrl;
    this.jwtHelper = new JwtHelperService();
    this.isLoginSubject = new BehaviorSubject<boolean>(this.isUserLoggedIn());
  }

  public login(loginForm: any) : Observable<User> {
    this.http.post<User>(`${this.host}/api/login`, loginForm, {observe: 'response'}).subscribe({
      next: (response : HttpResponse<User>) => {
        const token = response.headers.get(HeaderType.JWT_TOKEN)!;
        const refreshToken = response.headers.get(HeaderType.REFRESH_TOKEN)!;
        this.saveTokens(token, refreshToken);
        this.saveUser(response.body!);
        this.isLoginSubject.next(true);
    },     
      error: () => {
        this.isLoginSubject.next(false);
    }});

    return this.http.post<User>(`${this.host}/api/login`, loginForm, {observe: 'response'}).pipe(map((user : HttpResponse<User>) => user.body!));
  }


  public isLoggedIn() : Observable<boolean> {
    return this.isLoginSubject.asObservable();
  }

  public register(registerForm: any) : Observable<User> {
    return this.http.post<User>(`${this.host}/api/register`, registerForm);
  }

  public logout() : void {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    localStorage.removeItem('refresh-token');
    this.isLoginSubject.next(false);
  }

  public getToken() : string | null {
    return localStorage.getItem('token');
  }

  public getRefreshToken() : string | null {
    return localStorage.getItem('refresh-token');
  }



  private isUserLoggedIn() : boolean {
    let token : string | null = this.getToken();
    if (token !== null && token !== undefined) {   
      if (this.jwtHelper.decodeToken(token).sub !== null || '') {
        if (!this.jwtHelper.isTokenExpired(token)) {
          return true;
        }
      }
    } 
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    return false;

  }

  get host() : string {
    return this._host;
  }

  private saveTokens(token : string, refreshToken : string) {
    localStorage.setItem('token', token);
    localStorage.setItem('refresh-token', refreshToken);


  }
  private saveUser(user : User) {
    localStorage.setItem('user', JSON.stringify(user));
  }
}
