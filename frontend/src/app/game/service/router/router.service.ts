import { Injectable } from '@angular/core';
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class RouterService {

  constructor(private router: Router) { }

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

  redirectToRegister(){
    this.router.navigate(['/register']).catch(error => {
      console.error('Error navigating to /register:', error);
    });
  }

  redirectToUser(user:string){
    this.router.navigate(['/'+user]).catch(error => {
      console.error('Error navigating to /'+user, error);
    });
  }
}
