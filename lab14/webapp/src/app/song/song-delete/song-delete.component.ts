import { Component, OnInit } from '@angular/core';
import {SongService} from '../shared/song.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-song-delete',
  templateUrl: './song-delete.component.html',
  styleUrls: ['./song-delete.component.css']
})
export class SongDeleteComponent implements OnInit {

  songId: number;

  constructor(private service: SongService,
              private route: ActivatedRoute,
              private router: Router) { }

  ngOnInit(): void {
    this.songId = this.route.snapshot.params.id;
  }

  deleteSong(): void {
    this.service.deleteSong(this.songId).subscribe(
      success => {
        this.router.navigate(['songs/list']);
      },
      error => {
        console.log('error deleting artist with id' + this.songId);
      }
    );
  }

}
