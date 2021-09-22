import { Component, OnInit } from '@angular/core';
import {User} from '../shared/user.model';
import {UserService} from '../shared/user.service';

@Component({
  selector: 'app-user-filter',
  templateUrl: './user-filter.component.html',
  styleUrls: ['./user-filter.component.css']
})
export class UserFilterComponent implements OnInit {
  users: User[];
  errorHappened: boolean;

  constructor(private service: UserService) { }

  ngOnInit(): void {
    this.filterUsers('');
  }

  filterUsers(name: string): void {
    this.service.getUsers().subscribe(
      users => {
        this.users = users.filter(user => user.name.toLowerCase().includes(name.toLowerCase()));
      },
      error => {
        this.errorHappened = true;
      }
    );
  }
}
