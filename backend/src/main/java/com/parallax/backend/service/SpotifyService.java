package com.parallax.backend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parallax.backend.dto.SpotifyResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class SpotifyService {

    @Value("${spotify.api.client.id}")
    String clientID;
    @Value("${spotify.api.client.secret}")
    String clientSecret;
    String accessToken;
    Random random = new Random();
    SpotifyResponse daysTrack;


    public ResponseEntity<SpotifyResponse> draw(String url){
        int index = url.lastIndexOf("/");
        String newUrl  = "https://api.spotify.com/v1/playlists" + url.substring(index) + "/tracks";
        log.info(newUrl);

        JsonNode node = getJsonProperties(newUrl);

        JsonNode tracksNode = node.get("tracks").get("items");

        List<JsonNode> songs = new ArrayList<>();
        for (JsonNode item : tracksNode) {
            if (!item.get("track").get("preview_url").asText().equals("null")) {
                songs.add(item.get("track"));
            }
        }
        int range = random.nextInt(songs.size());
        var shuffledSong = songs.get(range);

        var response = new SpotifyResponse(
                shuffledSong.get("album").get("images").get(1).get("url").asText(),
                shuffledSong.get("preview_url").asText(),
                shuffledSong.get("name").asText(),
                shuffledSong.get("artists").get(0).get("name").asText(),
                shuffledSong.get("album").get("name").asText());

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @Scheduled(cron = "0 0 6 * * *")
    public void dayTrack(){
        String url = "https://open.spotify.com/playlist/0JiVp7Z0pYKI8diUV6HJyQ?si=56ff4692136140e1";
        this.daysTrack = draw(url).getBody();
    }

    public ResponseEntity<SpotifyResponse> getDayTrack(){
        return ResponseEntity.status(HttpStatus.OK).body(this.daysTrack);
    }

    public ResponseEntity<SpotifyResponse> drawFromTopStreamed(){
        String url = "https://open.spotify.com/playlist/0JiVp7Z0pYKI8diUV6HJyQ?si=56ff4692136140e1";
        return draw(url);
    }

    public ResponseEntity<SpotifyResponse> drawFrom2K() {
        String URL_2K = "https://open.spotify.com/playlist/3JxYCQeXAy64gZC1jXRP02?si=e0a944404bef4eae";
        return draw(URL_2K);
    }

    public ResponseEntity<SpotifyResponse> drawFrom90s() {
        String URL_90s = "https://open.spotify.com/playlist/37i9dQZF1DXdo6A3mWpdWx?si=815ac405c778499c";
        return draw(URL_90s);
    }

    public ResponseEntity<SpotifyResponse> drawFrom80s() {
        String URL_80s = "https://open.spotify.com/playlist/37i9dQZF1DXb57FjYWz00c?si=253f248313a14cf1";
        return draw(URL_80s);
    }

    public ResponseEntity<SpotifyResponse> drawFrom70s() {
        String URL_70s = "https://open.spotify.com/playlist/37i9dQZF1DWTJ7xPn4vNaz?si=503ec16abe9e46d9";
        return draw(URL_70s);
    }


    public void getAccessToken(){
        HttpClient client = HttpClient.newHttpClient();
        String URL = "https://accounts.spotify.com/api/token";

        String requestBody = "grant_type=" + URLEncoder.encode("client_credentials", StandardCharsets.UTF_8) +
                "&client_id=" + URLEncoder.encode(clientID, StandardCharsets.UTF_8) +
                "&client_secret=" + URLEncoder.encode(clientSecret, StandardCharsets.UTF_8);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node;
        try{
            node = mapper.readTree(response.body());
        } catch (IOException e){
            throw new RuntimeException(e);
        }

        this.accessToken = node.get("access_token").asText();

        log.info(response.body());
    }


    public boolean isIdValid(String id){

        var index = id.lastIndexOf("/");
        String userId = id.substring(index);
        String url = "https://api.spotify.com/v1/users" + userId;

       JsonNode node = getJsonProperties(url);

        return !node.get("error").get("status").asText().equals("500");
    }

    public JsonNode getJsonProperties(String url){
        if(accessToken == null){
            getAccessToken();
        }

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + accessToken)
                .build();

        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e){
            throw new RuntimeException(e);
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node;
        try{
            node = mapper.readTree(response.body());
        } catch (IOException e){
            throw new RuntimeException(e);
        }
        return node;
    }

}
