import { Challenge } from './../model/challenge';
import { Observable } from 'rxjs';
import { environment } from './../../../environments/environment';
import { HttpClient, HttpEvent, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthenticationService } from './authentication.service';
import { Flag } from '../model/flag';

@Injectable({
  providedIn: 'root'
})
export class ChallengeService {
  
  private host;
  private loggedId : any;

  constructor(private http: HttpClient, private authenticationService : AuthenticationService) {
    this.host = environment.apiUrl;
    this.authenticationService.userSubject.subscribe(u => this.loggedId = u?.id);
  }

  public createChallenge(challenge : Challenge) : Observable<any> {
    return this.http.post<any>(`${this.host}/api/challenge/new`, challenge)
  }

  public getChallenges() : Observable<Challenge[]> {
    return this.http.get<Challenge[]>(`${this.host}/api/challenge/all/${this.loggedId}`);
  }

  public getAllChallenges() : Observable<Challenge[]> {
    return this.http.get<Challenge[]>(`${this.host}/api/challenge/all`);
  }

  public deleteChallenges(selectedChallenges : Challenge[])  {
    return this.http.post<any>(`${this.host}/api/challenges/delete`, selectedChallenges)
  }

  public editChallenges(cloned: Challenge[]) : Observable<any> {
    return this.http.patch<any>(`${this.host}/api/challenges/edit`, cloned);
  }

  public findChallengeById(id : string): Observable<Challenge> {
    return this.http.get<Challenge>(`${this.host}/api/challenge/${id}`);
  }

  public findAllFlagsByChallengeId(id : string): Observable<Flag[]> {
    return this.http.get<Flag[]>(`${this.host}/api/flag/all/${id}`);
  }

  public editChallenge(challengeEditForm: any, id: string) : Observable<Challenge> {
    return this.http.patch<any>(`${this.host}/api/challenge/edit/${id}`, challengeEditForm);
  }

  public deleteFlag(id: string) {
    return this.http.delete<any>(`${this.host}/api/flag/delete/${id}`);
  }

  public createFlag(createFlagForm: any, challengeId: string) {
    return this.http.post<any>(`${this.host}/api/flag/create/${challengeId}`, createFlagForm);
  }

  public editFlag(updateFlagForm: any) {
    return this.http.patch<any>(`${this.host}/api/flag/edit`, updateFlagForm);
  }

  public getMissingChallengesForUser(id: string) {
    return this.http.get<Challenge[]>(`${this.host}/api/challenge/missing/${id}`);
  }

}
