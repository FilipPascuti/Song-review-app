import { Component, OnInit } from '@angular/core';
import {Artist} from '../shared/artist.model';
import {ArtistService} from '../shared/artist.service';

@Component({
  selector: 'app-artist-sorted',
  templateUrl: './artist-sorted.component.html',
  styleUrls: ['./artist-sorted.component.css']
})
export class ArtistSortedComponent implements OnInit {

  artists: Artist[];
  errorHappened: boolean;

  constructor(private service: ArtistService) { }

  ngOnInit(): void {
    this.getSorted();
  }

  getSorted(): void {
    this.service.getSorted().subscribe(
      artists => this.artists = artists,
      error => this.errorHappened = true
    );
  }
}
