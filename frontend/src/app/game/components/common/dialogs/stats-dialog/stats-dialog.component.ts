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
import {MatIcon} from "@angular/material/icon";
import {MatGridList, MatGridTile} from "@angular/material/grid-list";

@Component({
  selector: 'app-stats-dialog',
  standalone: true,
  imports: [
    MatButton,
    MatDialogActions,
    MatDialogClose,
    MatDialogContent,
    MatDialogTitle,
    MatIcon,
    MatGridList,
    MatGridTile
  ],
  templateUrl: './stats-dialog.component.html',
  styleUrl: './stats-dialog.component.scss'
})
export class StatsDialogComponent {

  constructor(public dialog: MatDialog, private dialogRef: MatDialogRef<StatsDialogComponent>) {
  }

  closeDialog() {
    this.dialogRef.close();

    document.getElementById('focusTarget')?.focus();
  }

}
