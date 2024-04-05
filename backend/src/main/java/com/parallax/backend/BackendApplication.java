package com.parallax.backend;

import com.parallax.backend.service.AnimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@Slf4j
@SpringBootApplication
public class BackendApplication implements CommandLineRunner {

    @Autowired
    AnimeService scrapper;



    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {




        scrapper.fillTheList();
        log.info("Roles deleted and recreated successfully");
    }


}
