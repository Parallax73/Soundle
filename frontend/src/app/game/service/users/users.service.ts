import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {RouterService} from "../router/router.service";

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  private readonly registerUrl = 'http://localhost:8080/api/v1/users/register';
  private readonly loginUrl = 'http://localhost:8080/api/v1/users/login';



  constructor(private http: HttpClient, private router: RouterService) {

  }


  save(username: string, email: string,password: string) {
    let bodyData = {
      "username": username,
      "email": email,
      "password": password
    };

    this.http.post(this.registerUrl, bodyData, {
      responseType: 'json'
    }).subscribe((result: any) => {
      this.router.redirectToLogin();
    });
  }


  login(username: string, password: string){
    let bodyData = {
      "username" : username,
      "password" : password
    };

    this.http.post(this.loginUrl, bodyData, {
      responseType: 'json'
    }).subscribe((result: any) =>{
      this.router.redirectToMenu();
    })
  }




}
