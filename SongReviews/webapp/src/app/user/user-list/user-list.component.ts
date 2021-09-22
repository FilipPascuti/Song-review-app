import {Component, OnInit} from '@angular/core';
import {UserService} from '../shared/user.service';
import {User} from '../shared/user.model';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  users: User[];
  displayedColumns: string[] = ['id', 'name'];

  constructor(private service: UserService) {
  }

  ngOnInit(): void {
    this.getUsers();
  }

  getUsers(): void {
    this.service.getUsers().subscribe(
      users => this.users = users
    );
  }

}
