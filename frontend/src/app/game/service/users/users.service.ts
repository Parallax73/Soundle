import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {RouterService} from "../router/router.service";
import {CookieService} from "ngx-cookie-service";
import {map, Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  private readonly registerUrl = 'http://localhost:8080/api/v1/users/register';
  private readonly loginUrl = 'http://localhost:8080/api/v1/users/login';
  private readonly detailsUrl = 'http://localhost:8080/api/v1/users/username'
  private userLogged: string = '';

  constructor(private http: HttpClient, private router: RouterService, private cookie: CookieService) {
  }

  save(username: string, email: string, password: string) {
    let bodyData = {
      "username": username,
      "email": email,
      "password": password
    };

    this.http.post(this.registerUrl, bodyData, {
      responseType: 'json'
    }).subscribe(() => {
      this.router.redirectToLogin();
    });
  }

  login(username: string, password: string) {
    let bodyData = {
      "username": username,
      "password": password
    };

    this.http.post(this.loginUrl, bodyData, {
      responseType: 'text'
    }).subscribe((result) => {
      const jwt = result.substring(16).replaceAll('"',"").replaceAll("}","");
      this.cookie.set("UserDetails", jwt, {secure: true});
      this.getUsername(jwt).subscribe(username => {
        this.userLogged = username;
      });
    });
  }


  getUsername(token: string): Observable<string> {
    return this.http.get(this.detailsUrl + "/" +  token, {
      responseType: 'text'
    }).pipe(
      map((result: string) => {
        return result.substring(16).replaceAll('"', "").replaceAll("}", "");
      })
    );
  }

  deleteCookies() {
    this.cookie.delete("UserDetails");
    this.router.redirectToMenu();
  }

  redirectToUser(){
    this.router.redirectToUser(this.userLogged);
  }


  isCookiePresent(): boolean {
    const userDetails = this.cookie.get("UserDetails");
    return !!(userDetails && userDetails.trim() !== "");

  }

}
