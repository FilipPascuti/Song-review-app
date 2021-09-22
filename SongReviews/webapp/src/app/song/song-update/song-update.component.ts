import { Component, OnInit } from '@angular/core';
import {Song} from '../shared/song.model';
import {SongService} from '../shared/song.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Form, FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-song-update',
  templateUrl: './song-update.component.html',
  styleUrls: ['./song-update.component.css']
})
export class SongUpdateComponent implements OnInit {

  songId: number;
  song: Song;
  errorHappened: boolean;
  songUpdateForm: FormGroup;

  constructor(private service: SongService,
              private builder: FormBuilder,
              private router: Router,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.songId = +this.route.snapshot.paramMap.get('id');
    this.loadSong();

    this.songUpdateForm = this.builder.group({
        title: ['',
          [
            Validators.required,
            Validators.pattern('([a-zA-Z ]+){5,}')
          ]],
        length: ['', [
          Validators.required,
          Validators.min(10),
          Validators.max(900)
        ]],
        key: ['', [
          Validators.required,
          Validators.pattern('^[a-gA-G]{1}$')
        ]]
      }
    );
  }

  private loadSong(): void {
    this.service.getSong(this.songId).subscribe(
      song => {
        this.song = song;
        console.log(this.song);
      },
      error => console.log('not existing song')
    );
  }

  updateSong(titleOf, lengthOf, keyOf): void {
    this.service.updateSong(
      {
        id: this.songId,
        details: {
          title: titleOf,
          length: lengthOf,
          key: keyOf
        },
        artist: this.song.artist
      }).subscribe(
      success => {
        this.router.navigate(['songs/list']);
      },
      error => {
        this.errorHappened = true;
      }
    );
  }

  get title(): any {
    return this.songUpdateForm.get('title');
  }

  get length(): any {
    return this.songUpdateForm.get('length');
  }

  get key(): any {
    return this.songUpdateForm.get('key');
  }
}
