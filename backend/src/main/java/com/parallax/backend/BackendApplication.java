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

        String checkSql = "SELECT COUNT(*) FROM role WHERE id = 0 AND role_name = 'ROLE_USER'";
        int count = jdbcTemplate.queryForObject(checkSql, Integer.class);

        if (count == 0) {
            String insertSql = "INSERT INTO role (id, role_name) VALUES (0, 'ROLE_USER')";
            jdbcTemplate.update(insertSql);
        }


        checkSql = "SELECT COUNT(*) FROM role WHERE id = 1 AND role_name = 'ROLE_ADMIN'";
        count = jdbcTemplate.queryForObject(checkSql, Integer.class);

        if (count == 0) {
            String insertSql2 = "INSERT INTO role (id, role_name) VALUES (1, 'ROLE_ADMIN')";
            jdbcTemplate.update(insertSql2);
            log.info("Roles added to the database");
        }

        scrapper.fillTheList();
        log.info("List filled successfully");
    }
}
