package com.parallax.backend.service;



import com.parallax.backend.utils.AnimeResponse;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class AnimeScrapper {

    String MAL_BY_POPULARITY1_LINK = "https://myanimelist.net/topanime.php?type=bypopularity&limit=";
    String MAL_ANIME_BY_RANKING_SELECTOR = ".anime_ranking_h3";
    List<Element> animeList = new ArrayList<>();
    Random random = new Random();

    public AnimeResponse draw() {
        String ANIME_MOE_URL = "https://animethemes.moe/anime/";
        String MOE_AUDIO_SELECTOR = "a.sc-c551d941-0.sc-c551d941-2.xAATx.iCLIxr";
        String title = null;
        int index = -1;

        try {
            String audioPage;
            Element element = null;
            int attempts = 0;
            while (element == null && attempts < animeList.size()) {
                index = random.nextInt(animeList.size());
                var drawnAnime = animeList.get(index)
                        .select("a").attr("href")
                        .replaceAll("__", "_").toLowerCase();
                title = animeList.get(index).text();

                int lastIndex = drawnAnime.lastIndexOf("/");
                String moeUrl = ANIME_MOE_URL + drawnAnime.substring(lastIndex + 1);

                try {
                    Document moeSite = Jsoup.connect(moeUrl).get();
                    element = moeSite.select(MOE_AUDIO_SELECTOR).first();
                } catch (HttpStatusException e) {
                    log.info("404 Error for index " + index + ", trying another...");
                    attempts++;
                }
            }

            if (element != null) {
                audioPage = ANIME_MOE_URL + element.select("a").attr("href").substring(7);

                Document document = Jsoup.connect(audioPage).get();
                Elements audioElement = document.select("meta[name=og:video]");
                Element metaTag = audioElement.first();
                String src = metaTag.attr("content");
                String audioUrl = src.replace("webm","ogg").replaceFirst("v","a");

                return AnimeResponse.builder()
                        .audioUrl(audioUrl)
                        .videoUrl(src)
                        .title(title)
                        .position(index+1).build();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void fillTheList() throws IOException {
        int position = 0;
        while (position < 500) {

            Document document = Jsoup.connect(MAL_BY_POPULARITY1_LINK + position).get();
            Elements elements = document.select(MAL_ANIME_BY_RANKING_SELECTOR);
            animeList.addAll(elements);
            position += 50;

        }
    }




}
