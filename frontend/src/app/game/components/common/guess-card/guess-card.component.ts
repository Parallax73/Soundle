import {Component, OnInit} from '@angular/core';
import {MatProgressSpinner, ProgressSpinnerMode} from "@angular/material/progress-spinner";
import {MatSlider, MatSliderThumb} from "@angular/material/slider";
import {MatButton} from "@angular/material/button";
import {MatIcon} from "@angular/material/icon";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-guess-card',
  standalone: true,
  imports: [
    MatProgressSpinner,
    MatSlider,
    MatSliderThumb,
    MatButton,
    MatIcon,
    NgIf
  ],
  templateUrl: './guess-card.component.html',
  styleUrl: './guess-card.component.scss'
})
export class GuessCardComponent  {
  mode: ProgressSpinnerMode = 'determinate';
  playOrPause = true;
  value: number = 0;
  maxValue: number = 100;



  changeToPause() {
    this.playOrPause = !this.playOrPause;
  }

  play(){

  }

 startCount(targetTime:number){
    const incrementPerMillisecond = this.maxValue / targetTime;
    const intervalDuration = 1 / incrementPerMillisecond;

    const interval = setInterval(() => {
      this.value += incrementPerMillisecond * intervalDuration;
      if (this.value >= this.maxValue) {
        clearInterval(interval);
        this.value = this.maxValue;
      }
    }, intervalDuration);

    setTimeout(() => {
      clearInterval(interval);
      this.value = this.maxValue;
    }, targetTime);
  }
}
