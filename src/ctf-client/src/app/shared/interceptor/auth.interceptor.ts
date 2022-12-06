import { environment } from 'src/environments/environment';
import { Router } from '@angular/router';
import { HeaderType } from '../enum/header-type.enum';
import { AuthenticationService } from '../service/authentication.service';
import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse,
  HttpResponse
} from '@angular/common/http';
import { catchError, Observable, BehaviorSubject, switchMap, filter, take, throwError } from 'rxjs';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  private host;
  private isRefreshing = false;
  private refreshTokenSubject: BehaviorSubject<any> = new BehaviorSubject<any>(null);

  constructor(private authenticationService: AuthenticationService, private router: Router) {
    this.host = environment.apiUrl;
  }

  intercept(httpRequest: HttpRequest<any>, HttpHandler: HttpHandler): Observable<HttpEvent<any>> {
    if (httpRequest.url.includes(`${this.host}/api/login`)) {
      return HttpHandler.handle(httpRequest);
    }
    if (httpRequest.url.includes(`${this.host}/api/register`)) {
      return HttpHandler.handle(httpRequest);
    }
    if (httpRequest.url.includes(`${this.host}/api/token/refresh`)) {
      return HttpHandler.handle(httpRequest);
    }


    const token = this.authenticationService.getToken();
    let authReq = httpRequest;
    if (token != null) {
      authReq = httpRequest.clone({ setHeaders: { Authorization: `Bearer ${token}` } });
    }
    return HttpHandler.handle(authReq).pipe(catchError(error => {
      if (error instanceof HttpErrorResponse && (!authReq.url.includes('/api/login') || !authReq.url.includes('/api/register')) && error.status === 401) {
        return this.handle401Error(authReq, HttpHandler);
      }
      return throwError(() => error.message);
    }));
  }
  private handle401Error(request: HttpRequest<any>, next: HttpHandler) {
    if (!this.isRefreshing) {
      this.isRefreshing = true;
      this.refreshTokenSubject.next(null);
      const token = this.authenticationService.getRefreshToken();
      if (token) {
        return this.authenticationService.refreshToken(token).pipe(
          switchMap((response: HttpResponse<any>) => {
            this.isRefreshing = false;
            this.authenticationService.saveJwtToken(response.headers.get(HeaderType.JWT_TOKEN)!);
            this.authenticationService.saveRefreshToken(response.headers.get(HeaderType.REFRESH_TOKEN)!);
            this.refreshTokenSubject.next(response.headers.get(HeaderType.JWT_TOKEN)!);

            return next.handle(this.addTokenHeader(request, response.headers.get(HeaderType.JWT_TOKEN)!));
          }),
          catchError(err => {
            this.isRefreshing = false;
            this.authenticationService.logout();
            this.router.navigate(['/login'], { queryParams: { returnUrl: this.router.url }});
            return throwError(() => err.message);
          })
        );
      }
    }
    return this.refreshTokenSubject.pipe(
      filter(token => token !== null),
      take(1),
      switchMap((token) => next.handle(this.addTokenHeader(request, token)))
    );
  }

  private addTokenHeader(request: HttpRequest<any>, token: string) {
    return request.clone({ headers: request.headers.set('Authorization', 'Bearer ' + token) });
  }

}


