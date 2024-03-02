import { Component } from '@angular/core';
import {FormControl, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatButton} from "@angular/material/button";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {ToolbarComponent} from "../common/toolbar/toolbar.component";
import {Router} from "@angular/router";

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
export class RegisterPageComponent {
  usernameFormControl = new FormControl('', [Validators.required, Validators.email, Validators.maxLength(50),Validators.minLength(5)]);

  constructor(private router: Router) {
  }

  redirectToLogin(){
    this.router.navigate(['/login']).catch(error => {
      console.error('Error navigating to /login:', error);
    });
  }
}
