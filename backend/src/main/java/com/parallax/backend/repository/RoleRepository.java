package com.parallax.backend.repository;


import com.parallax.backend.entity.Role;
import com.parallax.backend.enums.RoleList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRoleName(RoleList roleName);
    void deleteAllByRoleName(String roleName);
}