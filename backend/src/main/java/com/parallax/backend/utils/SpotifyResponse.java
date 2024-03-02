package com.parallax.backend.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SpotifyResponse {

    String imageUrl;
    String audioUrl;
    String title;
    String artist;
    String album;

}
