import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Artist} from '../../artist/shared/artist.model';
import {Song} from './song.model';

@Injectable({
  providedIn: 'root'
})
export class SongService {

  private url = 'http://localhost:8080/api/songs';

  constructor(private httpClient: HttpClient) { }

  getSongs(): Observable<Song[]> {
    return this.httpClient.get<Song[]>(this.url);
  }

  getSong(id: number): Observable<Song> {
    return this.httpClient.get<Song>(this.url + `/${id}`);
  }

  addSong(song: Song): Observable<HttpResponse<any>> {
    return this.httpClient.post<HttpResponse<any>>(this.url + '/add', song, {observe: 'response'});
  }

  updateSong(song: Song): Observable<HttpResponse<any>> {
    return this.httpClient.put<HttpResponse<any>>(this.url + '/update/', song, {observe: 'response'});
  }

  deleteSong(songId: number): Observable<HttpResponse<any>> {
    return this.httpClient.delete<HttpResponse<any>>(this.url + `/delete/${songId}`);
  }

}
