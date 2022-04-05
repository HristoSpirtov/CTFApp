import { environment } from 'src/environments/environment';
import { AuthenticationService } from './authentication.service';
import { Submission } from './../model/submission';
import { Observable, take } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

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
}
