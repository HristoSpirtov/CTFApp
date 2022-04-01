import { Component, OnInit, OnDestroy } from '@angular/core';
import { AuthenticationService } from 'src/app/shared/service/authentication.service';


@Component({
  selector: 'app-home',
  templateUrl: './challenge.component.html',
  styleUrls: ['./challenge.component.css']
})
export class ChallengeComponent implements OnInit {

  
  
  constructor(private authenticationService : AuthenticationService) { 
    
  }
  

  

  ngOnInit(): void {
    
  }

}
