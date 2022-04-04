import { Challenge } from './../model/challenge';
import { Observable } from 'rxjs';
import { environment } from './../../../environments/environment';
import { HttpClient, HttpEvent, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ChallengeService {

  private host;

  constructor(private http: HttpClient, ) {
    this.host = environment.apiUrl;
  }

  public createChallenge(challenge : Challenge[]) : Observable<any> {
    return this.http.post<any>(`${this.host}/api/challenge/new`, challenge)
  }

  public getChallenges() : Observable<Challenge[]> {
    return this.http.get<Challenge[]>(`${this.host}/api/challenge/all`);
  }

}
