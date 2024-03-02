import {Component, Input} from '@angular/core';
import {ToolbarComponent} from "../toolbar/toolbar.component";
import {NgOptimizedImage} from "@angular/common";
import {MatButton} from "@angular/material/button";


@Component({
  selector: 'app-art-card',
  standalone: true,
  imports: [
    ToolbarComponent,
    NgOptimizedImage,
    MatButton
  ],
  templateUrl: './art-card.component.html',
  styleUrl: './art-card.component.scss'
})
export class ArtCardComponent {

  @Input() imageUrl!: string;
  @Input() audioUrl!: string;
  @Input() title!: string;
  @Input() artist!: string;
  @Input() album!: string;
  @Input() streak!: number;
}
