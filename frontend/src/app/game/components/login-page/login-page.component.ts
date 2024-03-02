import { Component } from '@angular/core';
import {FormControl, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatError, MatFormField} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {NgIf} from "@angular/common";
import {MatLabel} from "@angular/material/form-field";
import {MatButton, MatIconButton} from "@angular/material/button";
import {MatList, MatListItem} from "@angular/material/list";
import {MatDivider} from "@angular/material/divider";
import {MatToolbar} from "@angular/material/toolbar";
import {MatIcon} from "@angular/material/icon";
import {GameComponent} from "../game/game.component";
import {ToolbarComponent} from "../common/toolbar/toolbar.component";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login-page',
  standalone: true,
  imports: [
    MatFormField,
    ReactiveFormsModule,
    MatInput,
    MatError,
    NgIf,
    MatLabel,
    MatButton,
    MatList,
    MatDivider,
    MatListItem,
    MatToolbar,
    MatIcon,
    MatIconButton,
    GameComponent,
    ToolbarComponent
  ],
  templateUrl: './login-page.component.html',
  styleUrl: './login-page.component.scss'
})
export class LoginPageComponent {
  usernameFormControl = new FormControl('', [Validators.required, Validators.email, Validators.maxLength(50),Validators.minLength(5)]);

  constructor(private router: Router) {
  }

  redirectToRegister(){
    this.router.navigate(['/register']).catch(error => {
      console.error('Error navigating to /register:', error);
    });
  }

}
