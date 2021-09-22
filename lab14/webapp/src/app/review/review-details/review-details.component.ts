import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Params} from '@angular/router';
import {ReviewService} from '../shared/review.service';
import {Review} from '../shared/review.model';
import {switchMap} from 'rxjs/operators';

@Component({
  selector: 'app-review-details',
  templateUrl: './review-details.component.html',
  styleUrls: ['./review-details.component.css']
})
export class ReviewDetailsComponent implements OnInit {

  review: Review;

  constructor(private service: ReviewService,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.getReview();
  }

  getReview(): void {
    this.route.params
      .pipe(switchMap((params: Params) => this.service.getReview(+params.userId, +params.songId)))
      .subscribe(review => this.review = review);
  }

}
