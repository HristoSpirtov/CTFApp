import { AuthenticationService } from './../service/authentication.service';
import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private authenticationService : AuthenticationService) {}

  intercept(httpRequest: HttpRequest<any>, HttpHandler: HttpHandler) : Observable<HttpEvent<any>> {
    if (httpRequest.url.includes(`${this.authenticationService.host}/api/login)`)) {
      return HttpHandler.handle(httpRequest);
    }
    if (httpRequest.url.includes(`${this.authenticationService.host}/api/register)`)) {
      return HttpHandler.handle(httpRequest);
    }

    this.authenticationService.loadToken();
    const token = this.authenticationService.getToken();
    //have to clone the request because it is imutable
    const request = httpRequest.clone({setHeaders : { Authoriaztion: `Bearer ${token}`}});
    return HttpHandler.handle(request);  
  }
}
