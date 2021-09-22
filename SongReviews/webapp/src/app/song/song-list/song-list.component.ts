import { Component, OnInit } from '@angular/core';
import {Song} from '../shared/song.model';
import {SongService} from '../shared/song.service';
import {element} from 'protractor';
import {Router} from '@angular/router';

@Component({
  selector: 'app-song-list',
  templateUrl: './song-list.component.html',
  styleUrls: ['./song-list.component.css']
})
export class SongListComponent implements OnInit {

  songs: Song[];
  selectedSong: Song;
  displayedColumns = ['id', 'title', 'update', 'delete', 'add-review'];

  constructor(private service: SongService,
              private router: Router) { }

  ngOnInit(): void {
    this.getSongs();
  }

  getSongs(): void {
    this.service.getSongs().subscribe(
      songs => this.songs = songs
    );
  }

  goToDetails(): void {
    this.router.navigate(['/songs/details', this.selectedSong.id]);
  }

  onSelect(song: Song): void {
    this.selectedSong = song;
  }

  routeToUpdate(id: number): void {
    this.router.navigate(['songs/update', id]);
  }

  routeToDelete(id: number): void {
    this.router.navigate(['songs/delete', id]);
  }

  routeToAddReview(id: number): void {
    this.router.navigate(['reviews/add', id]);
  }
}
