import { User } from '../model/user';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http'
import { BehaviorSubject, Observable } from 'rxjs';
import { JwtHelperService } from '@auth0/angular-jwt';


@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private _host;
  private jwtHelper;
  public isLoginSubject;
  public userSubject;


  constructor(private http: HttpClient) {
    this._host = environment.apiUrl;
    this.jwtHelper = new JwtHelperService();
    this.isLoginSubject = new BehaviorSubject<boolean>(this.isUserLoggedIn());
    this.userSubject = new BehaviorSubject<User | null>(JSON.parse(localStorage.getItem('user')!) as User);
  }

  public login(loginForm: any): Observable<HttpResponse<User>> {
    return this.http.post<User>(`${this.host}/api/login`, loginForm, { observe: 'response' });
  }

  public refreshToken(token: string): Observable<HttpResponse<any>> {
    const headers: HttpHeaders = new HttpHeaders({ 'Authorization': `Bearer ${token}` });

    return this.http.get(`${this.host}/api/token/refresh`, { headers, observe: 'response' })
  }

  public isLoggedIn(): Observable<boolean> {
    return this.isLoginSubject.asObservable();
  }

  public register(registerForm: any): Observable<User> {
    return this.http.post<User>(`${this.host}/api/register`, registerForm);
  }

  public logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    localStorage.removeItem('refresh-token');
    this.isLoginSubject.next(false);
    this.userSubject.next(null);
  }

  public getToken(): string | null {
    return localStorage.getItem('token');
  }

  public getRefreshToken(): string | null {
    return localStorage.getItem('refresh-token');
  }

  public getUser() {
    return this.userSubject.asObservable();

  }

  public getUserFromLocalStorage() {
    return localStorage.getItem('user');

  }

  private isUserLoggedIn(): boolean {
    let token: string | null = this.getToken();
    if (token !== null && token !== undefined) {
      return true;
    }
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    return false;

  }

  get host(): string {
    return this._host;
  }

  public saveJwtToken(token: string) {
    localStorage.setItem('token', token);
  }

  public saveRefreshToken(refreshToken: string) {
    localStorage.setItem('refresh-token', refreshToken);
  }
  public saveUser(user: User) {
    localStorage.setItem('user', JSON.stringify(user));
  }
}
