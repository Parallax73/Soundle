package com.parallax.backend.controller;


import com.parallax.backend.service.SpotifyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/v1/tracks")
public class TrackController {

    @Autowired
    SpotifyService service;

    @GetMapping("/day-track")
    public ResponseEntity<?> daysTrack(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getDayTrack());
    }

    @GetMapping("/draw-top")
    public ResponseEntity<?> drawTopStreamed(){
        return ResponseEntity.status(HttpStatus.OK).body(service.drawFromTopStreamed());
    }

    @GetMapping("/draw-2k")
    public ResponseEntity<?> draw2k(){
        return ResponseEntity.status(HttpStatus.OK).body(service.drawFrom2K());
    }

    @GetMapping("/draw-90s")
    public ResponseEntity<?> draw90s(){
        return ResponseEntity.status(HttpStatus.OK).body(service.drawFrom90s());
    }

    @GetMapping("/draw-80s")
    public ResponseEntity<?> draw80s(){
        return ResponseEntity.status(HttpStatus.OK).body(service.drawFrom80s());
    }

    @GetMapping("/draw-70s")
    public ResponseEntity<?> draw70s(){
        return ResponseEntity.status(HttpStatus.OK).body(service.drawFrom70s());
    }
}
