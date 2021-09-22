import { Component, OnInit } from '@angular/core';
import {Artist} from '../shared/artist.model';
import {ArtistService} from '../shared/artist.service';
import {ActivatedRoute, Params} from '@angular/router';

import {switchMap} from 'rxjs/operators';

@Component({
  selector: 'app-artist-details',
  templateUrl: './artist-details.component.html',
  styleUrls: ['./artist-details.component.css']
})
export class ArtistDetailsComponent implements OnInit {

  artist: Artist;

  constructor(private service: ArtistService,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.getArtist();
  }

  getArtist(): void{
    this.route.params
      .pipe(switchMap((params: Params) => this.service.getArtist(+params.id)))
      .subscribe(artist => this.artist = artist);
  }
}
