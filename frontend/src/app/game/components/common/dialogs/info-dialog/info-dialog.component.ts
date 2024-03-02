import { Component } from '@angular/core';
import {MatButton} from "@angular/material/button";
import {
  MatDialog,
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
  MatDialogRef,
  MatDialogTitle
} from "@angular/material/dialog";
import {MatGridList, MatGridTile} from "@angular/material/grid-list";
import {MatIcon} from "@angular/material/icon";
import {MatRadioButton, MatRadioGroup} from "@angular/material/radio";
import {MatSlideToggle} from "@angular/material/slide-toggle";

@Component({
  selector: 'app-info-dialog',
  standalone: true,
  imports: [
    MatButton,
    MatDialogActions,
    MatDialogClose,
    MatDialogContent,
    MatDialogTitle,
    MatGridList,
    MatGridTile,
    MatIcon,
    MatRadioButton,
    MatRadioGroup,
    MatSlideToggle
  ],
  templateUrl: './info-dialog.component.html',
  styleUrl: './info-dialog.component.scss'
})
export class InfoDialogComponent {

  constructor(public dialog: MatDialog, private dialogRef: MatDialogRef<InfoDialogComponent>) {
  }

  closeDialog() {
    this.dialogRef.close();

    document.getElementById('focusTarget')?.focus();
  }

}
