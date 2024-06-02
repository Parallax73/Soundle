Parallax Backend Service

This project is a Spring Boot application that provides various backend services, including interaction with MyAnimeList for anime data, Spotify for music data, and user authentication. The application is structured with distinct services to handle these functionalities.
Technologies Used

    Spring Boot: Framework for building the backend application.
    Lombok: For reducing boilerplate code with annotations.
    JSoup: For web scraping.
    Jackson: For JSON processing.
    JWT: For authentication.
    Spring Security: For securing the application.
    MySQL: As the database for storing user information.

Services
AnimeService

This service is responsible for fetching popular anime from MyAnimeList and retrieving anime themes from AnimeThemes.moe.
Methods

    fillTheList(): Fetches popular anime from MyAnimeList and populates an internal list.
    draw(): Randomly selects an anime from the list and fetches its theme song from AnimeThemes.moe.

SpotifyService

This service interacts with the Spotify API to retrieve and manage playlists and tracks.
Methods

    draw(String url): Fetches tracks from a Spotify playlist and randomly selects a track.
    dayTrack(): Schedules a daily task to select a random track from a predefined playlist.
    getDayTrack(): Returns the track selected by the daily task.
    drawFromTopStreamed(): Fetches and returns a random track from the top streamed playlist.
    drawFrom2K(): Fetches and returns a random track from the 2000s playlist.
    drawFrom90s(): Fetches and returns a random track from the 1990s playlist.
    drawFrom80s(): Fetches and returns a random track from the 1980s playlist.
    drawFrom70s(): Fetches and returns a random track from the 1970s playlist.
    getAccessToken(): Retrieves and stores the access token for the Spotify API.
    getJsonProperties(String url): Fetches and returns JSON properties from a given URL using the stored access token.

UserService

This service manages user authentication and registration.
Methods

    register(UserRegister register): Registers a new user.
    login(UserLogin login): Authenticates a user and returns a JWT token.
    authenticate(UserLogin login): Authenticates a user.
    getUsername(String token): Extracts the username from a JWT token.