# Soundle

## Overview
Parallax Backend Service is a robust Spring Boot application designed to offer a variety of backend services. It facilitates interactions with MyAnimeList for anime data, Spotify for music data, and includes user authentication features. The application architecture is modular, with dedicated services for each functionality.

## Technologies Used
- **Spring Boot**: The core framework used to build the backend application.
- **Lombok**: Utilized to minimize boilerplate code via annotations.
- **JSoup**: Employed for web scraping tasks.
- **Jackson**: Integrated for efficient JSON processing.
- **JWT**: Implemented for secure user authentication.
- **Spring Security**: Applied to enhance the security of the application.
- **MySQL**: The chosen database for persisting user information.

## Services

### AnimeService
Handles the retrieval of popular anime information from MyAnimeList and anime themes from AnimeThemes.moe.

#### Methods
- All methods require an authorization header with 'Bearer {token}'
- `fillTheList()`: Populates an internal list with popular anime fetched from MyAnimeList.
- `draw()`: Selects a random anime from the list and retrieves its theme song from AnimeThemes.moe.

### SpotifyService
Interacts with the Spotify API to manage playlists and tracks.

#### Methods
- `draw(String url)`: Obtains tracks from a Spotify playlist and selects one at random.
- `dayTrack()`: Sets up a daily task to pick a random track from a specific playlist.
- `getDayTrack()`: Provides the track chosen by the daily task.
- `drawFromTopStreamed()`: Retrieves a random track from the top streamed playlist.
- `drawFrom2K()`: Selects a random track from the 2000s playlist.
- `drawFrom90s()`: Chooses a random track from the 1990s playlist.
- `drawFrom80s()`: Picks a random track from the 1980s playlist.
- `drawFrom70s()`: Gets a random track from the 1970s playlist.
- `getAccessToken()`: Acquires and stores the access token needed for the Spotify API.
- `getJsonProperties(String url)`: Fetches and returns JSON properties from a specified URL using the stored access token.

### UserService
Manages user registration and authentication processes.

#### Methods
- `register(UserRegister register)`: Creates a new user account.
- `login(UserLogin login)`: Validates user credentials and issues a JWT token.
- `authenticate(UserLogin login)`: Confirms user authentication.
- `getUsername(String token)`: Extracts the username from a provided JWT token.
