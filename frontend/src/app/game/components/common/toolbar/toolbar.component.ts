import {Component, OnInit} from '@angular/core';
import {MatButton, MatIconButton} from "@angular/material/button";
import {MatIcon} from "@angular/material/icon";
import {MatMenu, MatMenuItem, MatMenuTrigger} from "@angular/material/menu";
import {MatNavList} from "@angular/material/list";
import {MatSidenav, MatSidenavContainer, MatSidenavContent} from "@angular/material/sidenav";
import {MatToolbar} from "@angular/material/toolbar";
import {NgIf} from "@angular/common";
import {MatDialog} from "@angular/material/dialog";
import { RouterOutlet} from "@angular/router";
import {DialogComponent} from "../dialogs/setting-dialog/setting-dialog.component";
import {InfoDialogComponent} from "../dialogs/info-dialog/info-dialog.component";
import {StatsDialogComponent} from "../dialogs/stats-dialog/stats-dialog.component";
import {RouterService} from "../../../service/router/router.service";
import {UsersService} from "../../../service/users/users.service";

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
    MatMenuTrigger,
    RouterOutlet
  ],
  templateUrl: './toolbar.component.html',
  styleUrl: './toolbar.component.scss'
})
export class ToolbarComponent implements OnInit{


  constructor(public dialog: MatDialog, public router: RouterService, public service: UsersService) {

  }

  ngOnInit() {
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




}
