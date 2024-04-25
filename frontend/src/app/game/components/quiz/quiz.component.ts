import {Component} from '@angular/core';
import {ToolbarComponent} from "../common/toolbar/toolbar.component";
import {MatCard, MatCardContent} from "@angular/material/card";
import {ArtCardComponent} from "../common/art-card/art-card.component";
import {MatButtonToggle} from "@angular/material/button-toggle";
import {MatError, MatFormField} from "@angular/material/form-field";
import {MatOption} from "@angular/material/autocomplete";
import {MatSelect} from "@angular/material/select";
import {NgForOf, NgIf} from "@angular/common";
import {FormBuilder, FormControl, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatProgressSpinner} from "@angular/material/progress-spinner";
import {TrackService} from "../../service/track/track.service";

interface Category {
  value: string;
  viewValue: string;
}

@Component({
  selector: 'app-quiz',
  standalone: true,
  imports: [
    ToolbarComponent,
    MatCard,
    MatCardContent,
    ArtCardComponent,
    MatButtonToggle,
    MatFormField,
    MatOption,
    MatSelect,
    NgForOf,
    NgIf,
    FormsModule,
    ReactiveFormsModule,
    MatError,
    MatProgressSpinner
  ],
  templateUrl: './quiz.component.html',
  styleUrl: './quiz.component.scss'
})
export class QuizComponent  {



  animeControl = new FormControl('', Validators.required);
  categoryControl = new FormControl('', Validators.required);
  quantityControl = new FormControl('', Validators.required);
  timeControl = new FormControl('', Validators.required);
  formGroup: any;
  service: any;

  constructor(private formBuilder: FormBuilder, service: TrackService) {
    this.service = service;
    this.formGroup = this.formBuilder.group({
      anime: this.animeControl,
      category: this.categoryControl,
      quantity: this.quantityControl,
      time: this.timeControl
    });
  }

  isCardVisible = true;
  isSpinnerVisible = false;
  selectedTime: string = '';
  selectedQuantity: string = '';
  selectedChoice: string = '';
  selectedCategory: Category | null = null;

  categories: Category[] = [
    { value: 'c-0', viewValue: 'Anime' },
    { value: 'c-1', viewValue: 'Top Streamed' },
    { value: 'c-2', viewValue: '2000s' },
    { value: 'c-3', viewValue: '90s' },
    { value: 'c-4', viewValue: '80s' },
    { value: 'c-5', viewValue: '70s' },
  ];

  times: Category[] = [
    { value: 't-0', viewValue: '15 seconds' },
    { value: 't-2', viewValue: '10 seconds' },
    { value: 't-3', viewValue: '5 seconds' },
    { value: 't-4', viewValue: '2 seconds' }
  ];

  quantity: Category[] = [
    { value: 'q-0', viewValue: '15' },
    { value: 'q-2', viewValue: '10' },
    { value: 'q-3', viewValue: '5' },
    { value: 'q-4', viewValue: 'Infinite' }
  ];

  animeChoices: Category[] = [
    { value: 'audio', viewValue: 'Audio Only' },
    { value: 'video', viewValue: 'Opening Video' }
  ];



  onShuffleClick() {
    if (this.formGroup.valid) {
      this.isCardVisible = false;
      this.isSpinnerVisible = true;
      this.service.teste();


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
    if (selectedCategory && selectedCategory.viewValue === 'Anime') {
      this.animeControl.setValidators([Validators.required]);
    } else {
      this.animeControl.clearValidators();
    }
    this.animeControl.updateValueAndValidity();
  }



}
