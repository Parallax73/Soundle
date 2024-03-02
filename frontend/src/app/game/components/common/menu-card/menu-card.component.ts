import {Component, Input, OnInit} from '@angular/core';
import {NgStyle} from "@angular/common";

@Component({
  selector: 'app-menu-card',
  standalone: true,
  imports: [
    NgStyle
  ],
  templateUrl: './menu-card.component.html',
  styleUrl: './menu-card.component.scss'
})
export class MenuCardComponent implements OnInit{

  @Input() title!: string;
  @Input() body!: string;
  @Input() height!: string;


  ngOnInit() {
    return;
  }
}
