import { Component, OnInit } from '@angular/core';
import {SongService} from '../shared/song.service';
import {ActivatedRoute, Router} from '@angular/router';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-song-add',
  templateUrl: './song-add.component.html',
  styleUrls: ['./song-add.component.css']
})
export class SongAddComponent implements OnInit {

  artistId: number;
  songForm: FormGroup;
  errorHappened: boolean;

  constructor(private service: SongService,
              private router: Router,
              private builder: FormBuilder,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.artistId = this.route.snapshot.params.id;
    console.log(this.artistId);

    this.songForm = this.builder.group({
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

  get title(): any {
    return this.songForm.get('title');
  }

  get length(): any {
    return this.songForm.get('length');
  }

  get key(): any {
    return this.songForm.get('key');
  }

  addSong(titleOf, lengthOf, keyOf): void {
    this.service.addSong(
      {
        id: 0,
        details: {
          title: titleOf,
          length: lengthOf,
          key: keyOf
        },
        artist: {
          id: this.artistId,
          name: '',
          description: ''
        }
      }).subscribe(
      success => {
        this.router.navigate(['/songs/list']);
      },
      error => {
        console.log(error);
        this.errorHappened = true;
      }
    );
  }
}
