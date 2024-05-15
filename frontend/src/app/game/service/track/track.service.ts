import { Injectable } from '@angular/core';
import {TokenService} from "../token/token.service";
import {HttpClient} from "@angular/common/http";
import {Anime} from "../../models/anime/anime";
import {catchError, map, Observable, throwError} from "rxjs";
import {Track} from "../../models/track/track";


@Injectable({
  providedIn: 'root'
})
export class TrackService {

  private readonly ANIME_URL = 'http://localhost:8080/api/v1/tracks/anime';
  private readonly TOP_URL = "http://localhost:8080/api/v1/tracks/draw-top";
  private readonly DAY_TRACK_URL = "http://localhost:8080/api/v1/tracks/day-track"
  private readonly URL_2000s = "http://localhost:8080/api/v1/tracks/draw-2k"
  private readonly URL_90s = "http://localhost:8080/api/v1/tracks/draw-90s"
  private readonly URL_80s = "http://localhost:8080/api/v1/tracks/draw-80s"
  private readonly URL_70s = "http://localhost:8080/api/v1/tracks/draw-70s"



  constructor(private token: TokenService, private http: HttpClient,) { }

  drawAnime(): Observable<Anime> {
    return this.http.get(this.ANIME_URL, {
      headers: {
        'Authorization': 'Bearer ' + this.token.getToken()
      },
      withCredentials: true
    }).pipe(
      map((result: any) => {
        let anime = new Anime();
        anime.audioLink = result.audioUrl;
        anime.name = result.title;
        anime.videoLink = result.videoUrl;
        anime.position = result.position;
        console.log(anime);
        return anime;
      }),
      catchError((error) => {
        console.log('Error:', error);
        return throwError(error);
      })
    );
  }

  drawTrack(url:string): Observable<Track>{
    return this.http.get(url, { headers: {
        'Authorization': 'Bearer ' + this.token.getToken()
      }, withCredentials: true })
      .pipe(
        map((result: any) => {
          let track = new Track();
          track.title = result.body.title;
          track.artist = result.body.artist;
          track.album = result.body.album;
          track.image = result.body.imageUrl;
          track.audio = result.body.audioUrl;
          return track;
        }),
        catchError((error) => {
          console.log('Error:', error);
          return throwError(error);
        })
      );
  }

  drawTop(): Observable<Track>{
    return this.drawTrack(this.TOP_URL)
  }

  drawDayTrack(): Observable<Track>{
    return this.drawTrack(this.DAY_TRACK_URL)
  }

  draw2000(): Observable<Track>{
    return this.drawTrack(this.URL_2000s)
  }

  draw90s(): Observable<Track>{
    return this.drawTrack(this.URL_90s)
  }

  draw80s(): Observable<Track>{
    return this.drawTrack(this.URL_80s)
  }

  draw70s(): Observable<Track>{
    return this.drawTrack(this.URL_70s)
  }







}
