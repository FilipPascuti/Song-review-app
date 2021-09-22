import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Artist} from './artist.model';

@Injectable({
  providedIn: 'root'
})
export class ArtistService {

  private url = 'http://localhost:8080/api/artists/';

  constructor(private httpClient: HttpClient) { }

  getArtists(): Observable<Artist[]> {
    return this.httpClient.get<Artist[]>(this.url);
  }

  addArtist(artist: Artist): Observable<HttpResponse<any>> {
    return this.httpClient.post<HttpResponse<any>>(this.url, artist, {observe: 'response'});
  }

  updateArtist(artist: Artist): Observable<HttpResponse<any>> {
    return this.httpClient.put<HttpResponse<any>>(this.url + artist.id, artist, {observe: 'response'});
  }

  getArtist(id: number): Observable<Artist> {
    return this.httpClient.get<Artist>(this.url + `${id}`);
  }

  deleteArtist(artistId: number): Observable<HttpResponse<any>> {
    return this.httpClient.delete<HttpResponse<any>>(this.url + `${artistId}`);
  }

  filterArtists(name: string): Observable<Artist[]>{
    return this.httpClient.get<Artist[]>(this.url + `filter/${name}`);
  }

  getSorted(): Observable<Artist[]> {
    return this.httpClient.get<Artist[]>(this.url + 'sorted');
  }
}
