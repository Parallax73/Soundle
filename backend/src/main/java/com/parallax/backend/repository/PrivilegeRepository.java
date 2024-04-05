package com.parallax.backend.repository;

import com.parallax.backend.entity.Privilege;
import com.parallax.backend.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege,Long> {

    Privilege findByName(String name);
}
