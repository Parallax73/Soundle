package com.parallax.backend.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AnimeResponse {

    String audioUrl;
    String videoUrl;
    String title;
    Integer position;

}
