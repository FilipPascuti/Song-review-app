import { Component, OnInit } from '@angular/core';
import {SongService} from '../../song/shared/song.service';
import {ActivatedRoute, Router} from '@angular/router';
import {ReviewService} from '../shared/review.service';

@Component({
  selector: 'app-review-delete',
  templateUrl: './review-delete.component.html',
  styleUrls: ['./review-delete.component.css']
})
export class ReviewDeleteComponent implements OnInit {

  userId: number;
  songId: number;

  constructor(private service: ReviewService,
              private route: ActivatedRoute,
              private router: Router) { }

  ngOnInit(): void {
    this.userId = this.route.snapshot.params.userId;
    this.songId = this.route.snapshot.params.songId;
  }

  deleteSong(): void {
    this.service.deleteReview(this.userId, this.songId).subscribe(
      success => {
        this.router.navigate(['reviews/list']);
      },
      error => {
        console.log('error deleting review');
      }
    );
  }

}
