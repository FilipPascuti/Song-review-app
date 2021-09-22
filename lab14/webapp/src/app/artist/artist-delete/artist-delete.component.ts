import { Component, OnInit } from '@angular/core';
import {ArtistService} from '../shared/artist.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-artist-delete',
  templateUrl: './artist-delete.component.html',
  styleUrls: ['./artist-delete.component.css']
})
export class ArtistDeleteComponent implements OnInit {

  artistId: number;

  constructor(private service: ArtistService,
              private route: ActivatedRoute,
              private router: Router) { }

  ngOnInit(): void {
    this.artistId = this.route.snapshot.params.id;
  }

  deleteArtist(): void {
    this.service.deleteArtist(this.artistId).subscribe(
      success => {
        this.router.navigate(['artists/list']);
      },
      error => {
        console.log('error deleting artist with id' + this.artistId);
      }
    );
  }
}
