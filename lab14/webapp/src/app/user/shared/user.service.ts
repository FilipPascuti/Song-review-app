import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {User} from './user.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private url = 'http://localhost:8080/api/users/';

  constructor(private httpClient: HttpClient) { }

  getUsers(): Observable<User[]> {
    return this.httpClient.get<User[]>(this.url);
  }

  addUser(user: User): Observable<HttpResponse<any>> {
    return this.httpClient.post<HttpResponse<any>>(this.url, user, {observe: 'response'});
  }

  updateUser(user: User): Observable<HttpResponse<any>> {
    return this.httpClient.put<HttpResponse<any>>(this.url + user.id, user, {observe: 'response'});
  }

}
