import { Component, OnInit } from '@angular/core';
import {UserService} from '../shared/user.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-user-update',
  templateUrl: './user-update.component.html',
  styleUrls: ['./user-update.component.css']
})
export class UserUpdateComponent implements OnInit {

  flag: boolean;

  constructor(private service: UserService,
              private router: Router) {
    this.flag = false;
  }

  ngOnInit(): void {
  }

  updateUser(idOf: number, nameOf: string): void {
    this.service.updateUser(
      {
        id: idOf,
        name: nameOf
      }).subscribe(
      response => {
        console.log(response);
        if (response.status === 200){
          console.log('update ok');
          this.router.navigate(['/users/list']);
        }
      },
      error => {
        console.log('update error');
        this.flag = true;
      }
    );
  }
}
