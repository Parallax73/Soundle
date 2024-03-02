import { Component } from '@angular/core';
import { MatButton } from "@angular/material/button";
import { MatSlideToggle } from "@angular/material/slide-toggle";
import {
  MatDialog,
  MatDialogActions, MatDialogClose,
  MatDialogContainer,
  MatDialogContent,
  MatDialogRef, MatDialogTitle
} from "@angular/material/dialog";
import {MatGridList, MatGridTile} from "@angular/material/grid-list";
import {MatRadioButton, MatRadioGroup} from "@angular/material/radio";
import {MatIcon} from "@angular/material/icon";


@Component({
  selector: 'app-dialog',
  standalone: true,
  imports: [
    MatButton,
    MatDialogActions,
    MatDialogContent,
    MatDialogContainer,
    MatDialogTitle,
    MatDialogClose,
    MatSlideToggle,
    MatGridList,
    MatGridTile,
    MatRadioGroup,
    MatRadioButton,
    MatIcon
  ],
  templateUrl: './setting-dialog.component.html',
  styleUrl: './setting-dialog.component.scss'
})
export class DialogComponent {

  constructor(public dialog: MatDialog, private dialogRef: MatDialogRef<DialogComponent>) {
  }

  closeDialog() {
    this.dialogRef.close();

    document.getElementById('focusTarget')?.focus();
  }


}
