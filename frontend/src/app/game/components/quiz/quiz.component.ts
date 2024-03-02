import { Component } from '@angular/core';
import {ToolbarComponent} from "../common/toolbar/toolbar.component";
import {MatCard, MatCardContent} from "@angular/material/card";
import {MenuCardComponent} from "../common/menu-card/menu-card.component";
import {ArtCardComponent} from "../common/art-card/art-card.component";

@Component({
  selector: 'app-quiz',
  standalone: true,
  imports: [
    ToolbarComponent,
    MatCard,
    MatCardContent,
    MenuCardComponent,
    ArtCardComponent
  ],
  templateUrl: './quiz.component.html',
  styleUrl: './quiz.component.scss'
})
export class QuizComponent {

}
