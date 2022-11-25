import { environment } from 'src/environments/environment';
import { AuthenticationService } from './authentication.service';
import { Submission } from './../model/submission';
import { Observable, take } from 'rxjs';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Solve } from '../model/solve';
import { Challenge } from '../model/challenge';
import { PieChartData } from '../model/pie-chart-data';
import { LineChartData } from '../model/line-chart-model';

@Injectable({
  providedIn: 'root'
})
export class SubmissionService {
  
  
  
  private host;

  constructor(private http: HttpClient, private authenticationService : AuthenticationService) {
    this.host = environment.apiUrl;
   }

  public createSubmission(submission : Submission) : Observable<Submission> {
    this.authenticationService.userSubject.pipe(take(1)).subscribe(u => {
      submission.user = u?.username!
      submission.school = u?.school!
    })
    return this.http.post<any>(`${this.host}/api/submission/new`, submission)
  }

  public getSubmissions(type : string, challengeId : string) : Observable<Submission[]> {
    return this.http.get<any>(`${this.host}/api/submissions/all/${type}/${challengeId}`)
  }

  public deleteSubmissions(selectedSubmissions: Submission[]) {
    let arr : string[] = [];
    selectedSubmissions.forEach(sub => arr.push(sub.id));
    return this.http.post<any>(`${this.host}/api/submissions/delete`, arr);
  }

  public getSolvesForUser(userId: string) : Observable<Solve[]> {
    return this.http.get<any>(`${this.host}/api/submissions/solves/${userId}`);
  }

  public getFailsForUser(userId: string) {
    return this.http.get<any>(`${this.host}/api/submissions/fails/${userId}`);
  }

  public solveChallengesByAdmin(selectedChallenges: Challenge[], userId: string) {
    
    let arr : string[] = [];
    selectedChallenges.forEach(sub => arr.push(sub.id));
    console.log(arr);
    
    return this.http.post<any>(`${this.host}/api/submissions/solve/${userId}`, arr);
  }

  public getLineChartArgs(userId: string) : Observable<LineChartData[]> {
    return this.http.get<any>(`${this.host}/api/chart/user/score/${userId}`);
  }
}
