import {Component, OnInit} from '@angular/core';
import {MatButton, MatIconButton} from "@angular/material/button";
import {MatIcon} from "@angular/material/icon";
import {MatMenu, MatMenuItem, MatMenuTrigger} from "@angular/material/menu";
import {MatNavList} from "@angular/material/list";
import {MatSidenav, MatSidenavContainer, MatSidenavContent} from "@angular/material/sidenav";
import {MatToolbar} from "@angular/material/toolbar";
import {NgIf} from "@angular/common";
import {MatDialog} from "@angular/material/dialog";
import {Router} from "@angular/router";
import {DialogComponent} from "../dialogs/setting-dialog/setting-dialog.component";
import {InfoDialogComponent} from "../dialogs/info-dialog/info-dialog.component";
import {StatsDialogComponent} from "../dialogs/stats-dialog/stats-dialog.component";

@Component({
  selector: 'app-toolbar',
  standalone: true,
  imports: [
    MatButton,
    MatIcon,
    MatIconButton,
    MatMenu,
    MatMenuItem,
    MatNavList,
    MatSidenav,
    MatSidenavContainer,
    MatSidenavContent,
    MatToolbar,
    NgIf,
    MatMenuTrigger
  ],
  templateUrl: './toolbar.component.html',
  styleUrl: './toolbar.component.scss'
})
export class ToolbarComponent implements OnInit{

  IS_USER_LOGGED: string;

  constructor(public dialog: MatDialog, private router: Router) {
    this.IS_USER_LOGGED= 'null';
  }


  ngOnInit() {
    localStorage.setItem("IS_USER_LOGGED","false")
  }

  openSettingDialog(){
    this.dialog.open(DialogComponent)
  }

  openInfoDialog(){
    this.dialog.open(InfoDialogComponent)
  }


  openStatsDialog(){
    this.dialog.open(StatsDialogComponent)
  }

  redirectToLogin() {
    this.router.navigate(['/login']).catch(error => {
      console.error('Error navigating to /login:', error);
    });
  }

  redirectToDoc(){
    this.router.navigate(['/documentation']).catch(error => {
      console.error('Error navigating to /documentation:', error);
    });
  }
  redirectToMenu(){
    this.router.navigate(['/game']).catch(error => {
      console.error('Error navigating to /game:', error);
    });
  }

  redirectToCode() {
    window.location.href = 'https://github.com/Parallax73/Soundle';
  }


}
