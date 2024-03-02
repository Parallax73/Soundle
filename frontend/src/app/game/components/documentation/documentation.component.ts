import { Component } from '@angular/core';
import {MatDrawerContainer, MatSidenav} from "@angular/material/sidenav";
import {MatButton, MatIconButton} from "@angular/material/button";
import {MatIcon} from "@angular/material/icon";
import {MatNavList} from "@angular/material/list";

@Component({
  selector: 'app-documentation',
  standalone: true,
  imports: [
    MatDrawerContainer,
    MatButton,
    MatIcon,
    MatIconButton,
    MatNavList,
    MatSidenav
  ],
  templateUrl: './documentation.component.html',
  styleUrl: './documentation.component.scss'
})
export class DocumentationComponent {

}
