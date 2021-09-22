import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {ArtistService} from '../shared/artist.service';
import {Artist} from '../shared/artist.model';

@Component({
  selector: 'app-artist-update',
  templateUrl: './artist-update.component.html',
  styleUrls: ['./artist-update.component.css']
})
export class ArtistUpdateComponent implements OnInit {

  artistId: number;
  artist: Artist;
  errorHappened: boolean;

  constructor(private service: ArtistService,
              private router: Router,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.artistId = +this.route.snapshot.paramMap.get('id');
    this.loadArtist();
  }

  loadArtist(): void {
    this.service.getArtist(this.artistId).subscribe(
      artist => this.artist = artist,
      error => console.log('inexisting artist')
    );
  }

  updateArtist(): void {
    this.service.updateArtist(this.artist).subscribe(
      success => {
        this.router.navigate(['artists/list']);
      },
      error => {
        this.errorHappened = true;
      }
    );
  }
}
