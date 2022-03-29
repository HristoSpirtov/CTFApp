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

    const token = this.authenticationService.getToken();
    //have to clone the request because it is imutable
    const request = httpRequest.clone({
      headers: httpRequest.headers.set('Authorization', 'Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJhdWQiOiJDVEZhIGNsaWVudCIsInN1YiI6ImgiLCJpc3MiOiJDVEZhIiwiZXhwIjoxNjQ4NTc3ODUzLCJpYXQiOjE2NDg1Nzc3OTMsImF1dGhvcml0aWVzIjpbIlJPTEVfUk9PVCIsIlJPTEVfVVNFUiJdfQ.8Yl1EaXsKE1uxpYzLVVG3l7lIlXHBCJgf0DmUnsTkkyHCeL55PcT5duPx8mbuIkqFx4GjqpANEEbC_0W1JhKAA')
    });
    return HttpHandler.handle(request);  
  }
}
