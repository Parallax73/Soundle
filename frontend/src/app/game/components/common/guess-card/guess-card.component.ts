import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';
import {MatProgressSpinner, ProgressSpinnerMode} from "@angular/material/progress-spinner";
import {MatSlider, MatSliderThumb} from "@angular/material/slider";
import {MatButton, MatIconButton} from "@angular/material/button";
import {MatIcon} from "@angular/material/icon";
import {AsyncPipe, NgForOf, NgIf, NgOptimizedImage} from "@angular/common";
import {MatButtonToggle} from "@angular/material/button-toggle";
import {MatFormField} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {FormControl, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {ToolbarComponent} from "../toolbar/toolbar.component";
import {MatAutocomplete, MatAutocompleteTrigger, MatOption} from "@angular/material/autocomplete";
import {map, Observable, of, startWith} from "rxjs";
import {AutocompleteService} from "../../../service/autocomplete/autocomplete.service";
import {RouterOutlet} from "@angular/router";



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
      ToolbarComponent,
      ReactiveFormsModule,
      MatAutocompleteTrigger,
      MatOption,
      MatAutocomplete,
      AsyncPipe,
      RouterOutlet
    ],
  templateUrl: './guess-card.component.html',
  styleUrl: './guess-card.component.scss'
})
  export class GuessCardComponent implements OnInit{

    @ViewChild('audioPlayer', { static: false }) audioPlayer!: ElementRef;
    @Input('source') source!: string;
    @Input('timer') timer!: number;
    userInput = "";
    myControl = new FormControl();
    volumeValue: number = 0;
    options: string[] = [];
    playOrPause = false;
    filteredOptions: Observable<string[]> = of([]);
    isAudioPlaying = false;
    interval: any = null;

    constructor(private autocomplete: AutocompleteService) {
      this.autocomplete.getNames().subscribe(names => this.options = names);
    }

    ngOnInit() {
      if (this.audioPlayer && this.audioPlayer.nativeElement) {
        console.log('audioPlayer:', this.audioPlayer);
        this.filteredOptions = this.myControl.valueChanges.pipe(
          startWith(''),
          map(value => this.autocomplete.filterNames(value))
        );
      }
    }

    checkAnswer(){
      console.log(this.userInput);
    }

    playAudio(): void {
      if (!this.isAudioPlaying) {
        this.audioPlayer.nativeElement.play().catch((error: any) => console.log('Playback failed because: ', error));
        this.isAudioPlaying = true;
        this.timerCounter();
      } else {
        this.audioPlayer.nativeElement.pause();
        this.isAudioPlaying = false;
        clearInterval(this.interval); // Stop the timer
      }
      this.playOrPause = this.isAudioPlaying;
    }

    timerCounter(){
      this.interval = setInterval(() => {
        if (this.timer > 0) {
          this.timer--;
        } else {
          this.audioPlayer.nativeElement.pause();
          this.audioPlayer.nativeElement.currentTime = 0;
          this.isAudioPlaying = false;
          this.playOrPause = this.isAudioPlaying;
          clearInterval(this.interval);
        }
      }, 1000)
    }


    volumeChange(volume:number){
      this.audioPlayer.nativeElement.volume = volume/100;
    }
  }



