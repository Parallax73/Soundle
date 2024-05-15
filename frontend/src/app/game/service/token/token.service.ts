import { Injectable } from '@angular/core';
import {CookieService} from "ngx-cookie-service";

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  constructor(private cookie: CookieService) { }

  getToken():any{
    const token = this.cookie.get('UserDetails');

    if (!token) {
      return null;
    }

    return token;
  }

  setToken(token:string):void{
    this.cookie.set("UserDetails", token, {secure: true});
  };

  deleteToken() {
    this.cookie.delete("UserDetails");
  }

  isTokenPresent(): boolean {
    const userDetails = this.cookie.get("UserDetails");
    return !!(userDetails && userDetails.trim() !== "");
  }
}
