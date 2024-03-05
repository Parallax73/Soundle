package com.parallax.backend;

import com.parallax.backend.service.AnimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@Slf4j
@SpringBootApplication
public class BackendApplication implements CommandLineRunner {

    @Autowired
    AnimeService scrapper;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        String sql = "insert into role (id, role_name)\n" +
                "values (1, 'ROLE_USER');";
        String sql2 = "insert into role (id, role_name)\n" +
                "values (2, 'ROLE_ADMIN');";
        jdbcTemplate.update(sql, sql2);

        scrapper.fillTheList();
        log.info("List filled successfully");
    }
}
