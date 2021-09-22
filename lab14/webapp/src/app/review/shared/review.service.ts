import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Song} from '../../song/shared/song.model';
import {Review} from './review.model';
import {UserStatistic} from './statistics-classes/userStatistic';
import {SongStatistic} from './statistics-classes/songStatistic';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  private url = 'http://localhost:8080/api/reviews/';

  constructor(private httpClient: HttpClient) { }

  getReviews(): Observable<Review[]> {
    return this.httpClient.get<Review[]>(this.url);
  }

  getReview(userId: number, songId: number): Observable<Review> {
    return this.httpClient.get<Review>(this.url + `${userId}/${songId}`);
  }

  addReview(review: Review): Observable<HttpResponse<any>> {
    return this.httpClient.post<HttpResponse<any>>(this.url + 'add', review, {observe: 'response'});
  }

  updateReview(review: Review): Observable<HttpResponse<any>> {
    return this.httpClient.put<HttpResponse<any>>(this.url + 'update/', review, {observe: 'response'});
  }

  deleteReview(userId: number, songId: number): Observable<HttpResponse<any>> {
    return this.httpClient.delete<HttpResponse<any>>(this.url + `delete/${userId}/${songId}`);
  }

  getActiveUsers(): Observable<UserStatistic[]> {
    return this.httpClient.get<UserStatistic[]>(this.url + 'active-users');
  }

  getSongAverages(): Observable<SongStatistic[]> {
    return this.httpClient.get<SongStatistic[]>(this.url + 'song-averages');
  }

}
