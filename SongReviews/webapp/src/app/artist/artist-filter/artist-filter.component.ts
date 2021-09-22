import { Component, OnInit } from '@angular/core';
import {Artist} from '../shared/artist.model';
import {ArtistService} from '../shared/artist.service';

@Component({
  selector: 'app-artist-filter',
  templateUrl: './artist-filter.component.html',
  styleUrls: ['./artist-filter.component.css']
})
export class ArtistFilterComponent implements OnInit {
  artists: Artist[];
  errorHappened: boolean;

  constructor(private service: ArtistService) { }

  ngOnInit(): void {
    this.filterArtists('');
  }

  filterArtists(name: string): void {
    this.service.filterArtists(name).subscribe(
      artists => this.artists = artists,
      error => this.errorHappened = true
    );
  }
}
