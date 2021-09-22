import { Component, OnInit } from '@angular/core';
import {UserStatistic} from '../shared/statistics-classes/userStatistic';
import {ReviewService} from '../shared/review.service';

@Component({
  selector: 'app-user-statistic',
  templateUrl: './user-statistic.component.html',
  styleUrls: ['./user-statistic.component.css']
})
export class UserStatisticComponent implements OnInit {

  users: UserStatistic[];
  displayedColumns = ['user', 'activity'];

  constructor(private service: ReviewService) { }

  ngOnInit(): void {
    this.getUsers();
  }

  getUsers(): void {
    this.service.getActiveUsers().subscribe(
      users => this.users = users
    );
  }

}
