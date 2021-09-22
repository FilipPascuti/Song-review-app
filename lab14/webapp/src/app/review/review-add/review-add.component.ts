import { Component, OnInit } from '@angular/core';
import {ReviewService} from '../shared/review.service';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../user/shared/user.service';
import {User} from '../../user/shared/user.model';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-review-add',
  templateUrl: './review-add.component.html',
  styleUrls: ['./review-add.component.css']
})
export class ReviewAddComponent implements OnInit {

  songId: number;
  users: User[];
  reviewAddForm: FormGroup;
  errorHappened: boolean;

  constructor(private reviewService: ReviewService,
              private userService: UserService,
              private builder: FormBuilder,
              private router: Router,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.songId = this.route.snapshot.params.songId;
    this.getUsers();

    this.reviewAddForm = this.builder.group({
        user: ['',
          [
            Validators.required
          ]],
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

  getUsers(): void {
    this.userService.getUsers().subscribe(
      users => this.users = users
    );
  }

  get text(): any {
    return this.reviewAddForm.get('text');
  }

  get stars(): any {
    return this.reviewAddForm.get('stars');
  }

  get user(): any {
    return this.reviewAddForm.get('user');
  }

  addReview(userIdOf, starsOf, text): void {
    this.reviewService.addReview(
      {
        id: 0,
        user: {
          id: userIdOf,
          name: 'placeholder'
        },
        song: {
          id: this.songId,
          details: {
            title: 'placeholder',
            length: 100,
            key: 'A'
          },
          artist: {
            id: 0,
            name: '',
            description: ''
          }
        },
        stars: starsOf,
        reviewText: text
      }).subscribe(
      success => {
        this.router.navigate(['/reviews/list']);
      },
      error => {
        console.log(error);
        this.errorHappened = true;
      }
    );
  }
}
