package com.parallax.backend.service;



import com.parallax.backend.dto.AnimeResponse;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class AnimeService {

    String MAL_BY_POPULARITY1_LINK = "https://myanimelist.net/topanime.php?type=bypopularity&limit=";
    String MAL_ANIME_BY_RANKING_SELECTOR = ".anime_ranking_h3";
    List<Element> animeList = new ArrayList<>();
    Random random = new Random();

    public ResponseEntity<AnimeResponse> draw() throws IOException {
        String ANIME_MOE_API_URL = "https://api.animethemes.moe/search?fields%5Bsearch%5D=anime&q=";
        String ANIME_MOE_URL = "https://animethemes.moe/anime/";
        String MOE_AUDIO_SELECTOR = "div.sc-64f8332-0:nth-child(2) > div:nth-child(2) > div:nth-child(3) > div:nth-child(1)";

        String animeSlug;
        try {

            JSONArray animeArray = new JSONArray();
            JSONObject firstAnime = new JSONObject();
            int index = 0;

            while (animeArray.isEmpty()) {
                index = random.nextInt(animeList.size());
                var anime = animeList.get(index);
                animeSlug = anime.text()
                        .replaceAll("[^a-zA-Z0-9]", " ")
                        .replaceAll(" ", "+")
                        .toLowerCase();

                log.info(animeSlug);
                String jsonResponse = Jsoup.connect(ANIME_MOE_API_URL + animeSlug)
                        .ignoreContentType(true)
                        .execute()
                        .body();

                JSONObject jsonObject = new JSONObject(jsonResponse);
                JSONArray animeResults = jsonObject.getJSONObject("search").getJSONArray("anime");

                if (animeResults.length() > 0) {
                    firstAnime = animeResults.getJSONObject(0);
                    animeArray.put(firstAnime);
                }
            }

            String slug = firstAnime.getString("slug");

            Document moeSite = Jsoup.connect(ANIME_MOE_URL + slug).get();
            log.info(ANIME_MOE_URL + slug);
            Element element = moeSite.select(MOE_AUDIO_SELECTOR).first();

            String audioPage = ANIME_MOE_URL + element.select("a").attr("href").substring(7);
            log.info(audioPage + " AUDIOPAGE");

            Document document = Jsoup.connect(audioPage).get();
            Elements audioElement = document.select("meta[name=og:video]");
            Element metaTag = audioElement.first();
            String src = metaTag.attr("content");
            String audioUrl = src.replace("webm", "ogg").replaceFirst("v", "a");

            var response = new AnimeResponse(
                    audioUrl,
                    src,
                    firstAnime.getString("name"),
                    index+1
                 );
                 log.info(response.title() + response.audioUrl() + response.position() + response.videoUrl());
                 return ResponseEntity.status(HttpStatus.OK).body(response);



        } catch (HttpStatusException e) {
            log.info(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


    public void fillTheList() throws IOException {
        int position = 0;
        while (position < 500) {

            Document document = Jsoup.connect(MAL_BY_POPULARITY1_LINK + position).get();
            Elements elements = document.select(MAL_ANIME_BY_RANKING_SELECTOR);
            for (Element e: elements){
                log.info(e.text());
                animeList.add(e);
            }
            position += 50;

        }
    }






}
