import {Component, OnInit} from '@angular/core';
import {RouterOutlet} from "@angular/router";
import {GameComponent} from "./game/components/game/game.component";
import {NgIf} from "@angular/common";



@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  standalone: true,
  imports: [
    RouterOutlet,
    GameComponent,
    NgIf
  ]
})
export class AppComponent implements OnInit{

  title = 'Soundle';


  showWaves = true;

  ngOnInit() {
    const urlParts = window.location.href.split('/');
    const href = urlParts[urlParts.length - 1];
    if (href === 'documentation'){
      this.showWaves=false;
    }
  }


}
