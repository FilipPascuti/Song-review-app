import { Component, OnInit } from '@angular/core';
import {User} from '../shared/user.model';
import {UserService} from '../shared/user.service';

@Component({
  selector: 'app-user-sorted',
  templateUrl: './user-sorted.component.html',
  styleUrls: ['./user-sorted.component.css']
})
export class UserSortedComponent implements OnInit {

  users: User[];

  constructor(private service: UserService) { }

  ngOnInit(): void {
    this.initializeSortedUsers();
  }

  initializeSortedUsers(): void {
    this.service.getUsers().subscribe(
      users => {
        this.users = users;
        this.users.sort((a, b) => (a.name.toLowerCase() > b.name.toLowerCase()) ? 1 : -1);
      }
    );
  }
}
