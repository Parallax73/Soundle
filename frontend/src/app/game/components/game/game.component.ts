import {Component, OnInit} from '@angular/core';

import {NgIf} from "@angular/common";
import {ToolbarComponent} from "../common/toolbar/toolbar.component";
import {RouterOutlet} from "@angular/router";
import {MatList, MatListItem} from "@angular/material/list";
import {MatButton} from "@angular/material/button";

@Component({
  selector: 'app-game',
  standalone: true,
  imports: [
    NgIf,
    ToolbarComponent,
    RouterOutlet,
    MatList,
    MatListItem,
    MatButton
  ],
  templateUrl: './game.component.html',
  styleUrl: './game.component.scss'
})
export class GameComponent {



}
