package com.parallax.backend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parallax.backend.dto.SpotifyResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SpotifyServiceTest {

    @InjectMocks
    private SpotifyService spotifyService;

    @Mock
    private HttpClient mockHttpClient;

    @Mock
    private HttpResponse<String> mockHttpResponse;

    @Mock
    private Random random;

    @Mock
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() throws IOException, InterruptedException {
        spotifyService.clientID = "test-client-id";
        spotifyService.clientSecret = "test-client-secret";
        spotifyService.accessToken = "test-access-token";

        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockHttpResponse);

        spotifyService.random = random;
    }

    @Test
    public void testDraw_Success() throws IOException {
        String playlistUrl = "https://api.spotify.com/v1/playlists/0JiVp7Z0pYKI8diUV6HJyQ/tracks";
        String jsonResponse = "{\"tracks\":{\"items\":[{\"track\":{\"preview_url\":\"https://test.url/preview\",\"name\":\"Test Song\",\"artists\":[{\"name\":\"Test Artist\"}],\"album\":{\"name\":\"Test Album\",\"images\":[{\"url\":\"image1\"},{\"url\":\"image2\"}]}}}]}}";

        when(mockHttpResponse.body()).thenReturn(jsonResponse);

        JsonNode mockJsonNode = new ObjectMapper().readTree(jsonResponse);
        when(objectMapper.readTree(anyString())).thenReturn(mockJsonNode);
        when(random.nextInt(anyInt())).thenReturn(0);

        ResponseEntity<SpotifyResponse> responseEntity = spotifyService.draw(playlistUrl);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        SpotifyResponse response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals("Test Song", response.title());
        assertEquals("Test Artist", response.artist());
        assertEquals("Test Album", response.album());
        assertEquals("https://test.url/preview", response.audioUrl());
        assertEquals("image2", response.imageUrl());
    }

    @Test
    public void testDraw_Failure() throws IOException, InterruptedException {
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(IOException.class);

        String playlistUrl = "https://api.spotify.com/v1/playlists/0JiVp7Z0pYKI8diUV6HJyQ/tracks";
        assertThrows(RuntimeException.class, () -> spotifyService.draw(playlistUrl));
    }

    @Test
    public void testDayTrack() {
        spotifyService.dayTrack();
        assertNotNull(spotifyService.daysTrack);
    }

    @Test
    public void testGetDayTrack() {
        spotifyService.daysTrack = new SpotifyResponse("image", "preview", "name", "artist", "album");
        ResponseEntity<SpotifyResponse> responseEntity = spotifyService.getDayTrack();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(spotifyService.daysTrack, responseEntity.getBody());
    }

    @Test
    public void testDrawFromTopStreamed() {
        ResponseEntity<SpotifyResponse> responseEntity = spotifyService.drawFromTopStreamed();
        assertNotNull(responseEntity);
    }

    @Test
    public void testDrawFrom2K() {
        ResponseEntity<SpotifyResponse> responseEntity = spotifyService.drawFrom2K();
        assertNotNull(responseEntity);
    }

    @Test
    public void testDrawFrom90s() {
        ResponseEntity<SpotifyResponse> responseEntity = spotifyService.drawFrom90s();
        assertNotNull(responseEntity);
    }

    @Test
    public void testDrawFrom80s() {
        ResponseEntity<SpotifyResponse> responseEntity = spotifyService.drawFrom80s();
        assertNotNull(responseEntity);
    }

    @Test
    public void testDrawFrom70s() {
        ResponseEntity<SpotifyResponse> responseEntity = spotifyService.drawFrom70s();
        assertNotNull(responseEntity);
    }

    @Test
    public void testGetAccessToken() {
        String tokenResponse = "{\"access_token\":\"new-access-token\"}";
        when(mockHttpResponse.body()).thenReturn(tokenResponse);

        spotifyService.getAccessToken();

        assertEquals("new-access-token", spotifyService.accessToken);
    }

    @Test
    public void testGetJsonProperties() {
        String jsonResponse = "{\"key\":\"value\"}";
        when(mockHttpResponse.body()).thenReturn(jsonResponse);

        JsonNode node = spotifyService.getJsonProperties("https://api.spotify.com/v1/playlists");

        assertNotNull(node);
        assertEquals("value", node.get("key").asText());
    }
}
