package com.parallax.backend.dto;


public record AnimeResponse(
        String audioUrl,
        String videoUrl,
        String title,
        Integer position)
{ }
