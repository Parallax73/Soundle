package com.parallax.backend.dto;

public record SpotifyResponse(
        String imageUrl,
        String audioUrl,
        String title,
        String artist,
        String album)
{

}
