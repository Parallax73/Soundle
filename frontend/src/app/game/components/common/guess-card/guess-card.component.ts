import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {MatProgressSpinner, ProgressSpinnerMode} from "@angular/material/progress-spinner";
import {MatSlider, MatSliderThumb} from "@angular/material/slider";
import {MatButton, MatIconButton} from "@angular/material/button";
import {MatIcon} from "@angular/material/icon";
import {NgForOf, NgIf, NgOptimizedImage} from "@angular/common";
import {MatButtonToggle} from "@angular/material/button-toggle";
import {MatFormField} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {FormsModule} from "@angular/forms";
import {ToolbarComponent} from "../toolbar/toolbar.component";


  @Component({
  selector: 'app-guess-card',
  standalone: true,
    imports: [
      MatProgressSpinner,
      MatSlider,
      MatSliderThumb,
      MatButton,
      MatIcon,
      NgIf,
      MatButtonToggle,
      MatFormField,
      MatInput,
      NgForOf,
      NgOptimizedImage,
      MatIconButton,
      FormsModule,
      ToolbarComponent
    ],
  templateUrl: './guess-card.component.html',
  styleUrl: './guess-card.component.scss'
})
  export class GuessCardComponent implements OnInit{
    @ViewChild('audioPlayer') audioPlayer!: ElementRef
    mode: ProgressSpinnerMode = 'indeterminate';
    playOrPause = true;

    volumeValue: any = null;
    isAudioPlaying: boolean = false;
    timer: number = 15;
    interval: any = null;
    audioSource = "";
    loading = true;
    userInput = "";

    volumeChange(){
      this.audioPlayer.nativeElement.volume = this.volumeValue/100;
    }

    playAudio(): void {
      if(!this.isAudioPlaying) {
        this.audioPlayer.nativeElement.play();
        this.isAudioPlaying = true;
        this.timerCounter()
      } else {
        this.audioPlayer.nativeElement.pause();
        this.audioPlayer.nativeElement.currentTime = 0;
        this.isAudioPlaying = false;
        this.timer = 15;
        clearInterval(this.interval);
      }
    }

    ngOnInit() {
      setTimeout(() => {
        this.loading = false;
      }, 3000);
    }


    timerCounter(){
      this.interval = setInterval(() => {
        if (this.timer>0) {
          this.timer--;
        } else {
          clearInterval(this.interval);
        }
      }, 1000)
    }

    checkAnswer(){
      console.log(this.userInput);
    }

    play() {
      this.playAudio();
      this.playOrPause = !this.playOrPause;
    }
  }



