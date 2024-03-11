package com.parallax.backend.entity;

import com.parallax.backend.enums.RoleList;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
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

}
