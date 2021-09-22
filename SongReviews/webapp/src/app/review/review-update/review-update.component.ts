import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Review} from '../shared/review.model';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {ReviewService} from '../shared/review.service';
import {switchMap} from 'rxjs/operators';

@Component({
  selector: 'app-review-update',
  templateUrl: './review-update.component.html',
  styleUrls: ['./review-update.component.css']
})
export class ReviewUpdateComponent implements OnInit {

  review: Review;
  errorHappened: boolean;
  reviewUpdateForm: FormGroup;

  constructor(private service: ReviewService,
              private builder: FormBuilder,
              private router: Router,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.loadSong();

    this.reviewUpdateForm = this.builder.group({
        stars: ['',
          [
            Validators.required,
            Validators.min(1),
            Validators.max(5)
          ]],
        text: ['', [
          Validators.required,
          Validators.pattern('([a-zA-Z ]+){5,}')
        ]]
      }
    );
  }

  loadSong(): void {
    this.route.params
      .pipe(switchMap((params: Params) => this.service.getReview(+params.userId, +params.songId)))
      .subscribe(review => this.review = review);
  }

  get text(): any {
    return this.reviewUpdateForm.get('text');
  }

  get stars(): any {
    return this.reviewUpdateForm.get('stars');
  }

  updateReview(starsOf, textOf): void {
    this.service.updateReview(
      {
        id: this.review.id,
        user: this.review.user,
        song: this.review.song,
        stars: starsOf,
        reviewText: textOf
      }).subscribe(
      success => {
        this.router.navigate(['reviews/list']);
      },
      error => {
        this.errorHappened = true;
      }
    );
  }
}
