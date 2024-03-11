import {Component, OnInit} from '@angular/core';
import {
  FormsModule,
  ReactiveFormsModule
} from "@angular/forms";
import {MatButton} from "@angular/material/button";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {ToolbarComponent} from "../common/toolbar/toolbar.component";


import {HttpClient} from "@angular/common/http";
import {RouterService} from "../../service/router/router.service";
import {UsersService} from "../../service/users/users.service";



@Component({
  selector: 'app-register-page',
  standalone: true,
  imports: [
    FormsModule,
    MatButton,
    MatFormField,
    MatInput,
    MatLabel,
    ToolbarComponent,
    ReactiveFormsModule
  ],
  templateUrl: './register-page.component.html',
  styleUrl: './register-page.component.scss'
})
export class RegisterPageComponent implements OnInit {

  username: string = "";
  email: string = "";
  password: string = "";


  constructor(
    public router: RouterService,
    public service: UsersService) {
  }

  ngOnInit() {
  }

  register(){
    this.service.save(this.username,this.email,this.password)
  }


}
