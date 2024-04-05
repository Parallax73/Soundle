import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class TrackService {

  private readonly spotifyDrawUrl = 'http://localhost:8080/api/v1/tracks/draw-top';

  constructor(private http: HttpClient, private cookie: CookieService) { }

  drawTopTracks() {
    const token = this.cookie.get('UserDetails');

    if (!token) {
      return;
    }

    this.http.get(this.spotifyDrawUrl, { headers: {
        'Authorization': 'Bearer ' + token
      }, withCredentials: true })
      .subscribe(
        (result: any) => {
          console.log(result.toString());
        },
        (error) => {
          console.log('Error:', error);
        }
      );
  }
}
