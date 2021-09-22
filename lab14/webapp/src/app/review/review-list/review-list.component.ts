import { Component, OnInit } from '@angular/core';
import {ReviewService} from '../shared/review.service';
import {Review} from '../shared/review.model';
import {Router} from '@angular/router';

@Component({
  selector: 'app-review-list',
  templateUrl: './review-list.component.html',
  styleUrls: ['./review-list.component.css']
})
export class ReviewListComponent implements OnInit {

  reviews: Review[];
  selectedReview: Review;
  displayedColumns = ['user', 'song', 'stars', 'update', 'delete'];

  constructor(private service: ReviewService,
              private router: Router) { }

  ngOnInit(): void {
    this.getReviews();
  }

  getReviews(): void {
    this.service.getReviews().subscribe(
      reviews => {
        this.reviews = reviews;
        console.log(this.reviews);
      }
    );
  }

  goToDetails(): void {
    this.router.navigate(['/reviews/details', this.selectedReview.user.id, this.selectedReview.song.id]);
  }

  onSelect(review: Review): void {
    this.selectedReview = review;
  }

  routeToUpdate(userId: number, songId: number): void {
    this.router.navigate(['reviews/update', userId, songId]);
  }

  routeToDelete(userId: number, songId: number): void {
    this.router.navigate(['reviews/delete', userId, songId]);
  }

}


