import { Injectable } from '@angular/core';
import {Observable, of} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {TokenService} from "../token/token.service";
import {error} from "@angular/compiler-cli/src/transformers/util";

@Injectable({
  providedIn: 'root'
})
export class AutocompleteService {

  private animeNames: string[] = [];
  private trackNames: Object = [];

  private readonly GET_ANIME_NAMES = 'http://localhost:8080/api/v1/animeList';
  private readonly GET_PLAYLIST_NAMES = 'http://localhost:8080/api/v1/trackList/';
  private readonly TOP_URL = "0JiVp7Z0pYKI8diUV6HJyQ?si=56ff4692136140e1";
  private readonly URL_2K = "3JxYCQeXAy64gZC1jXRP02?si=e0a944404bef4eae";
  private readonly URL_90s = "37i9dQZF1DXdo6A3mWpdWx?si=815ac405c778499c";
  private readonly URL_80s = "37i9dQZF1DXb57FjYWz00c?si=253f248313a14cf1";
  private readonly URL_70s = "37i9dQZF1DWTJ7xPn4vNaz?si=503ec16abe9e46d9";



  constructor(private token: TokenService,private http: HttpClient) { }


  filterNames(value: string): string[]{
    const  filterValue = value.toLowerCase();
    return this.animeNames.filter(name =>
    name.toLowerCase().includes(filterValue))
  }

  getAnimeName():Promise<string[]>{
    return new Promise((resolve,reject) => {
      this.http.get(this.GET_ANIME_NAMES, {
        headers: {
          'Authorization': 'Bearer ' + this.token.getToken()
        },
        withCredentials: true
      }).subscribe(response => {
        this.trackNames = response;
        resolve(this.trackNames);
      }, error => {
        console.error('Error')
        reject(error);
      })
    })
  }

  getTracksName(playlist: string): Promise<string[]> {
    return new Promise((resolve, reject) => {
      this.http.get(this.GET_PLAYLIST_NAMES + playlist, {
        headers: {
          'Authorization': 'Bearer ' + this.token.getToken()
        },
        withCredentials: true
      }).subscribe(response => {
        this.trackNames = response;
        resolve(this.trackNames);
      }, error => {
        console.error('Error fetching playlist names:', error);
        reject(error);
      });
    });
  }



  getTopNames(): Promise<string[]> {
   return  this.getTracksName(this.TOP_URL);
  }

  get2kNames(): Promise<string[]> {
    return  this.getTracksName(this.URL_2K);
  }

  get90Names(): Promise<string[]> {
    return  this.getTracksName(this.URL_90s);
  }

  get80Names(): Promise<string[]> {
    return  this.getTracksName(this.URL_80s);
  }

  get70Names(): Promise<string[]> {
    return  this.getTracksName(this.URL_70s);
  }



}

