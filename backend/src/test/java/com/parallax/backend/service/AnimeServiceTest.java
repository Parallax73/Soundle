package com.parallax.backend.service;

import com.parallax.backend.dto.AnimeResponse;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AnimeServiceTest {

    @InjectMocks
    private AnimeService animeService;

    @Mock
    private Random random;

    @BeforeEach
    public void setUp() {
        animeService.animeList = new ArrayList<>();
    }

    @Test
    public void testDraw_Success() throws IOException {

        Element mockElement = mock(Element.class);
        when(mockElement.text()).thenReturn("Mock Anime");
        animeService.animeList.add(mockElement);


        when(random.nextInt(anyInt())).thenReturn(0);

        String jsonResponse = "{\"search\":{\"anime\":[{\"slug\":\"mock-slug\",\"name\":\"Mock Anime\"}]}}";
        Connection mockConnection = mock(Connection.class);
        Connection.Response mockResponse = mock(Connection.Response.class);
        when(mockResponse.body()).thenReturn(jsonResponse);
        when(mockConnection.ignoreContentType(true)).thenReturn(mockConnection);
        when(mockConnection.execute()).thenReturn(mockResponse);
        when(Jsoup.connect(anyString())).thenReturn(mockConnection);


        Document mockDocument = mock(Document.class);
        when(Jsoup.connect(anyString()).get()).thenReturn(mockDocument);

        Element audioElement = mock(Element.class);
        when(audioElement.select(anyString())).thenReturn(new Elements(audioElement));
        when(audioElement.attr(anyString())).thenReturn("/mock-audio-url");

        when(mockDocument.select(anyString())).thenReturn(new Elements(audioElement));

        ResponseEntity<AnimeResponse> responseEntity = animeService.draw();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        AnimeResponse response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals("Mock Anime", response.title());
    }

    @Test
    public void testDraw_Failure() throws IOException {

        when(Jsoup.connect(anyString()).get()).thenThrow(IOException.class);

        ResponseEntity<AnimeResponse> responseEntity = animeService.draw();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    public void testFillTheList() throws IOException {

        Document mockDocument = mock(Document.class);
        Elements mockElements = new Elements();
        for (int i = 0; i < 50; i++) {
            Element mockElement = mock(Element.class);
            when(mockElement.text()).thenReturn("Mock Anime " + i);
            mockElements.add(mockElement);
        }
        when(Jsoup.connect(anyString()).get()).thenReturn(mockDocument);
        when(mockDocument.select(anyString())).thenReturn(mockElements);

        animeService.fillTheList();

        assertEquals(50, animeService.animeList.size());
    }
}