package com.parallax.backend.repository;

import com.parallax.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsername(String username);

    @Query( value = "SELECT username, streak" +
            " FROM soundle.users ORDER BY streak desc ", nativeQuery = true)
    List<User> rankingByScore();

    @Query( value = "SELECT username, score" +
            " FROM soundle.users ORDER BY score desc ", nativeQuery = true)
    List<User> rankingByStreak();
}
