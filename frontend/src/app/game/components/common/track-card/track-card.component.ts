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
  templateUrl: './track-card.component.html',
  styleUrl: './track-card.component.scss'
})
export class TrackCardComponent {

  @Input() imageUrl!: string;
  @Input() audioUrl!: string;
  @Input() title!: string;
  @Input() artist!: string;
  @Input() album!: string;
  /*@Input() streak!: number;*/



}
