import { Injectable } from '@angular/core';
import { SignupRequest } from '../model/signup-request.model';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { BehaviorSubject, Observable, Subscription, throwError } from 'rxjs';
import { LoggedUser } from '../model/logged-user.model';
import { LoginRequest } from '../model/login-request.model';
import { map, catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  loggedUserSubject: BehaviorSubject<LoggedUser>;
  loggedUser: Observable<LoggedUser>;

  private baseUrl: string = environment.protocol + '://' + environment.domain + ':' + environment.port + environment.api + '/auth';
  private stubUrl: string = environment.protocol + '://' + environment.domain + ':' + environment.port + environment.api + '/stub';


  constructor(private httpClient: HttpClient,
    private router: Router) {
      this.loggedUserSubject = new BehaviorSubject<LoggedUser>(JSON.parse(localStorage.getItem('LoggedUser')));
      this.loggedUser = this.loggedUserSubject.asObservable();
  }

  signup(data: SignupRequest): Observable<SignupRequest> {
    console.log(this.baseUrl);
    return this.httpClient.post<SignupRequest>(this.baseUrl + '/signup', data).pipe(catchError(this.handleException));
  }

  login(data: LoginRequest): Observable<any> {
    return this.httpClient.post<any>(this.baseUrl + '/login', data).pipe(catchError(this.handleException)).pipe(map((res: LoggedUser) => {
      localStorage.setItem('token', JSON.stringify(res.token));
      this.loggedUserSubject.next(res);
      // console.log(this.loggedUser)
    }));
  }

  getLoggedUser() {
    return this.loggedUser;
  }

  getToken() {
      return localStorage.getItem('token');
  }

  logout() {
    localStorage.removeItem('token');
    this.loggedUserSubject.next(null);
    this.loggedUser = this.loggedUserSubject.asObservable();
    this.router.navigate(['/auth']);
  }

  whoami() {
    const tok = localStorage.getItem('token');
    // console.log('whoami token', tok)
    if(tok) {
      return this.httpClient.get<any>(this.baseUrl + '/whoami');
    }
    else {
      this.router.navigate(['/auth']);
      return new Observable();
    }
  }

  confirmEmail(token: string) {
    return this.httpClient.put(this.baseUrl + '/confirm', token);
  }

  testRole(): Observable<string> {
    return this.httpClient.get<string>(this.stubUrl + '/test-auth-role');
  }

  testPermission(): Observable<string> {
    return this.httpClient.get<string>(this.stubUrl + '/test-auth-permission');
  }

  private handleException(err: HttpErrorResponse): Observable<never> {
    return throwError(err.error);
  }
}