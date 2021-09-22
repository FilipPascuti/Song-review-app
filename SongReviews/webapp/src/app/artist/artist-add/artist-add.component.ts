import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ArtistService} from '../shared/artist.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-artist-add',
  templateUrl: './artist-add.component.html',
  styleUrls: ['./artist-add.component.css']
})
export class ArtistAddComponent implements OnInit {

  artistForm: FormGroup;
  errorHappened: boolean;

  constructor(private builder: FormBuilder,
              private service: ArtistService,
              private router: Router) { }

  ngOnInit(): void {
    this.artistForm = this.builder.group({
        name: ['',
        [
          Validators.required,
          Validators.pattern('([a-zA-Z ]+){5,}')
        ]],
        description: ['', [
          Validators.required,
          Validators.pattern('([a-zA-Z ]+){5,}')
        ]]
      }
    );
  }

  get name(): any {
    return this.artistForm.get('name');
  }
  get description(): any {
    return this.artistForm.get('description');
  }

  addArtist(nameOf: string, descriptionOf: string): void {
    this.service.addArtist(
      {
        id: -1,
        name: nameOf,
        description: descriptionOf
      }
    ).subscribe(
      success => {
        this.router.navigate(['/artists/list']);
      },
      error => {
        console.log(error);
        this.errorHappened = true;
      }
    );
  }
}
