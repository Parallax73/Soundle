import {Component, OnInit} from '@angular/core';
import {GameOptionsComponent} from "../common/game-options/game-options.component";
import {NgForOf, NgIf} from "@angular/common";
import {GuessCardComponent} from "../common/guess-card/guess-card.component";
import {ToolbarComponent} from "../common/toolbar/toolbar.component";
import {MatProgressSpinner, ProgressSpinnerMode} from "@angular/material/progress-spinner";
import {MatButtonToggle} from "@angular/material/button-toggle";
import {FormBuilder, FormControl, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatError, MatFormField} from "@angular/material/form-field";
import {MatOption} from "@angular/material/autocomplete";
import {MatSelect} from "@angular/material/select";
import {TrackService} from "../../service/track/track.service";
import {Track} from "../../models/track/track";
import {Observable} from "rxjs";


interface Category {
  value: string;
  viewValue: string;
}

@Component({
  selector: 'app-quiz',
  standalone: true,
  imports: [
    GameOptionsComponent,
    NgIf,
    GuessCardComponent,
    ToolbarComponent,
    MatProgressSpinner,
    MatButtonToggle,
    FormsModule,
    MatError,
    MatFormField,
    MatOption,
    MatSelect,
    NgForOf,
    ReactiveFormsModule

  ],
  templateUrl: './quiz.component.html',
  styleUrl: './quiz.component.scss'
})
export class QuizComponent implements OnInit{
  source = "";
  loading = true;
  mode: ProgressSpinnerMode = 'indeterminate';

  timer: number = 0;
  quantity: number = 0;
  category!: Category;
  hasBeenSelect: boolean = false;
  choice = "";
  track!: Track

  animeControl = new FormControl('', Validators.required);
  categoryControl = new FormControl('', Validators.required);
  quantityControl = new FormControl('', Validators.required);
  timeControl = new FormControl('', Validators.required);

  formGroup: any;
  service: any;

  isCardVisible = true;
  isSpinnerVisible = false;

  constructor(private formBuilder: FormBuilder, service: TrackService) {
    this.service = service;
    this.formGroup = this.formBuilder.group({
      anime: this.animeControl,
      category: this.categoryControl,
      quantity: this.quantityControl,
      time: this.timeControl
    });
  }

  categories: Category[] = [
    { value: 'c-0', viewValue: 'Anime' },
    { value: 'c-1', viewValue: 'Top Streamed' },
    { value: 'c-2', viewValue: '2000s' },
    { value: 'c-3', viewValue: '90s' },
    { value: 'c-4', viewValue: '80s' },
    { value: 'c-5', viewValue: '70s' },
  ];

  times: Category[] = [
    { value: '15', viewValue: '15 seconds' },
    { value: '10', viewValue: '10 seconds' },
    { value: '5', viewValue: '5 seconds' },
    { value: '2', viewValue: '2 seconds' }
  ];

  quantityOptions: Category[] = [
    { value: '15', viewValue: '15' },
    { value: '10', viewValue: '10' },
    { value: '5', viewValue: '5' },
    { value: '20', viewValue: '20' }
  ];

  animeChoices: Category[] = [
    { value: 'audio', viewValue: 'Audio Only' },
    { value: 'video', viewValue: 'Opening Video' }
  ];

  onShuffleClick() {
    if (this.formGroup.valid) {
      this.isCardVisible = false;
      this.isSpinnerVisible = true;
      this.hasBeenSelect = true;
      this.timer = this.formGroup.get("time").value;
      this.shuffleSong()
    } else {
      for (const name in this.formGroup.controls) {
        if (this.formGroup.controls[name].invalid) {
          this.formGroup.controls[name].markAsTouched();
        }
      }
    }
  }

  onChange(categoryValue: string) {
    const selectedCategory = this.categories.find(category => category.value === categoryValue);
    if (selectedCategory) {
      if (selectedCategory.viewValue === 'Anime') {
        this.animeControl.setValidators([Validators.required]);
      } else {
        this.animeControl.clearValidators();
      }
      this.animeControl.updateValueAndValidity();
    }
  }

  shuffleSong(){
    this.choice = this.formGroup.get("category").value;
    switch (this.choice){
      case "c-0": // anime
        this.shuffleLoop(this.service.drawAnime.bind(this.service));
        break;
      case "c-1": //top
        this.shuffleLoop(this.service.drawTop.bind(this.service));
        break;
      case "c-2": // 2000s
        this.shuffleLoop(this.service.draw2000().bind(this.service));
        break;
      case "c-3": // 90s
        this.shuffleLoop(this.service.draw90s.bind(this.service));
        break;
      case "c-4": // 80s
        this.shuffleLoop(this.service.draw80s.bind(this.service));
        break;
      case "c-5": // 70s
        this.shuffleLoop(this.service.draw70s.bind(this.service));
        break;
    }
  }

  shuffleLoop(serviceCall: () => Observable<Track>){
   /* this.quantity = this.formGroup.get("quantity").value;
    while(this.quantity > 0){*/
    serviceCall().subscribe(track => {
      this.track = track;
    });

    console.log(this.track.audio , this.track.artist, this.track.album, this.track.title);

     /* this.quantity--;
    }*/
  }



  ngOnInit(): void {
    setTimeout(() => {
      this.loading = false;
    }, 3000);
  }





}
