import { Routes } from '@angular/router';
import {LoginPageComponent} from "./game/components/login-page/login-page.component";
import {QuizComponent} from "./game/components/quiz/quiz.component";
import {DocumentationComponent} from "./game/components/documentation/documentation.component";
import {RegisterPageComponent} from "./game/components/register-page/register-page.component";

export const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'game'},
  {
    path: 'game',
    loadChildren: () => import('./game/game.module').then(m => m.GameModule)
  },
  {
    path: 'login',
    component: LoginPageComponent
  },
  {
    path: 'register',
    component: RegisterPageComponent
  },
  {
    path: 'play',
    component: QuizComponent
  },
  {
    path: 'documentation',
    component: DocumentationComponent
  },
  { path: '**',
    redirectTo: ''
  }
];
