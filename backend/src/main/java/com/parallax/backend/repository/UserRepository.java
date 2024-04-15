package com.parallax.backend.repository;

import com.parallax.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    boolean existsByUsername(String username);

    Optional<User> findUserByUsername(String username);

    UserDetails findByUsername(String username);
}
