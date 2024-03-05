package com.parallax.backend.entity;

import com.parallax.backend.enums.RoleList;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @Enumerated(EnumType.STRING)
    private RoleList roleName;
    public Role() {
    }
    public Role(@NotNull RoleList roleName) {
        this.roleName = roleName;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public RoleList getRoleName() {
        return roleName;
    }
    public void setRoleName(RoleList roleName) {
        this.roleName = roleName;
    }

}
