import { Component, OnInit } from '@angular/core';
import {UserService} from '../shared/user.service';
import {User} from '../shared/user.model';
import {Router} from '@angular/router';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-user-add',
  templateUrl: './user-add.component.html',
  styleUrls: ['./user-add.component.css']
})
export class UserAddComponent implements OnInit {

  userForm: FormGroup;
  flag: boolean;

  constructor(private service: UserService,
              private builder: FormBuilder,
              private router: Router) {
    this.flag = false;
  }

  ngOnInit(): void {
    this.userForm = this.builder.group({
        name: ['',
          [
            Validators.required,
            Validators.pattern('([a-zA-Z ]+){5,}')
          ]]
      }
    );
  }

  addUser(nameOf: string): void {
    this.service.addUser(
      {
        id: 0,
        name: nameOf
      }).subscribe(
        response => {
          console.log(response);
          if (response.status === 200){
            this.router.navigate(['/users/list']);
          }
        },
        error => this.flag = true
    );
  }

  get name(): any {
    return this.userForm.get('name');
  }
}
