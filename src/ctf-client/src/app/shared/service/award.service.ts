import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Award } from '../model/award';

@Injectable({
  providedIn: 'root'
})
export class AwardService {
  
  private host; 

  constructor(private http: HttpClient) {
    this.host = environment.apiUrl;
   }

   public awardUser(form: Award) : Observable<any> {  
    return this.http.post<Award>(`${this.host}/api/award/new`, form);
  }

  public findAllAwardsByUserName(userId: string): Observable<Award[]> {
    return this.http.get<Award[]>(`${this.host}/api/award/all/${userId}`);
  }

  public deleteAwards(selectedAwards: Award[]) {
    let arr : string[] = [];
    selectedAwards.forEach(sub => arr.push(sub.id));
    return this.http.post<any>(`${this.host}/api/awards/delete`, arr);
  }

}
