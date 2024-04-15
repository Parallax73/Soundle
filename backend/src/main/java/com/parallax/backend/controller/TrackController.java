package com.parallax.backend.controller;


import com.parallax.backend.service.AnimeService;
import com.parallax.backend.service.SpotifyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Slf4j
@RequestMapping("api/v1/tracks")
public class TrackController {

    final
    SpotifyService service;

    final
    AnimeService animeService;

    public TrackController(SpotifyService service, AnimeService animeService) {
        this.service = service;
        this.animeService = animeService;
    }


    @Operation(
            description = "Endpoint for drawing some anime opening",
            responses = {
                    @ApiResponse(
                            description = "Anime drew correctly ",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Something went wrong drawing the anime",
                            responseCode = "400"
                    )
            }
    )
    @GetMapping("/anime")
    public ResponseEntity<?> drawAnime() throws IOException {
        return animeService.draw();
    }

    @Operation(
            description = "Endpoint for drawing some anime opening",
            responses = {
                    @ApiResponse(
                            description = "Anime drew correctly ",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Something went wrong drawing the anime",
                            responseCode = "400"
                    )
            }
    )
    @GetMapping("/day-track")
    public ResponseEntity<?> daysTrack(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getDayTrack());
    }

    @Operation(
            description = "Endpoint for drawing some anime opening",
            responses = {
                    @ApiResponse(
                            description = "Anime drew correctly ",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Something went wrong drawing the anime",
                            responseCode = "400"
                    )
            }
    )
    @GetMapping("/draw-top")
    public ResponseEntity<?> drawTopStreamed(){
        log.info("draw-top was called");
        return ResponseEntity.status(HttpStatus.OK).body(service.drawFromTopStreamed());
    }

    @Operation(
            description = "Endpoint for drawing a track from the 2000s",
            responses = {
                    @ApiResponse(
                            description = "Track drew correctly ",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Something went wrong drawing the track",
                            responseCode = "400"
                    )
            }
    )
    @GetMapping("/draw-2k")
    public ResponseEntity<?> draw2k(){
        return ResponseEntity.status(HttpStatus.OK).body(service.drawFrom2K());
    }

    @Operation(
            description = "Endpoint for drawing a track from the 90s",
            responses = {
                    @ApiResponse(
                            description = "Track drew correctly ",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Something went wrong drawing the track",
                            responseCode = "400"
                    )
            }
    )
    @GetMapping("/draw-90s")
    public ResponseEntity<?> draw90s(){
        return ResponseEntity.status(HttpStatus.OK).body(service.drawFrom90s());
    }

    @Operation(
            description = "Endpoint for drawing a track from the 80s",
            responses = {
                    @ApiResponse(
                            description = "Track drew correctly ",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Something went wrong drawing the track",
                            responseCode = "400"
                    )
            }
    )
    @GetMapping("/draw-80s")
    public ResponseEntity<?> draw80s(){
        return ResponseEntity.status(HttpStatus.OK).body(service.drawFrom80s());
    }

    @Operation(
            description = "Endpoint for drawing a track from the 70s",
            responses = {
                    @ApiResponse(
                            description = "Track drew correctly ",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Something went wrong drawing the track",
                            responseCode = "400"
                    )
            }
    )
    @GetMapping("/draw-70s")
    public ResponseEntity<?> draw70s(){
        return ResponseEntity.status(HttpStatus.OK).body(service.drawFrom70s());
    }


}
