import { Component, OnInit } from '@angular/core';
import {Song, SongDetails} from '../shared/song.model';
import {SongService} from '../shared/song.service';
import {ActivatedRoute, Params} from '@angular/router';
import {switchMap} from 'rxjs/operators';

@Component({
  selector: 'app-song-details',
  templateUrl: './song-details.component.html',
  styleUrls: ['./song-details.component.css']
})
export class SongDetailsComponent implements OnInit {

  song: Song;

  constructor(private service: SongService,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.getSong();
  }

  getSong(): void {
    this.route.params
      .pipe(switchMap((params: Params) => this.service.getSong(+params.id)))
      .subscribe(song => this.song = song);
  }

}
