import { Component, OnInit } from '@angular/core';
import {Artist} from '../shared/artist.model';
import {ArtistService} from '../shared/artist.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-artist-list',
  templateUrl: './artist-list.component.html',
  styleUrls: ['./artist-list.component.css']
})
export class ArtistListComponent implements OnInit {

  artists: Artist[];
  selectedArtist: Artist;
  displayedColumns: string[] = ['id', 'name', 'update', 'delete', 'add-song'];

  constructor(private service: ArtistService,
              private router: Router) { }

  ngOnInit(): void {
    this.getArtists();
  }

  getArtists(): void{
    this.service.getArtists().subscribe(
      artists => this.artists = artists
    );
  }

  onSelect(artist: Artist): void {
    this.selectedArtist = artist;
  }

  goToDetails(): void {
    this.router.navigate(['/artists/details', this.selectedArtist.id]);
  }

  routeToUpdate(id: number): void {
    this.router.navigate(['artists/update', id]);
  }

  routeToDelete(id: number): void {
    this.router.navigate(['artists/delete', id]);
  }

  routeToAddSong(id: number): void {
    this.router.navigate(['songs/add', id]);
  }

}
