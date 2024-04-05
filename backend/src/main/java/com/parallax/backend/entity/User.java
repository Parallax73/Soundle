package com.parallax.backend.entity;

import com.parallax.backend.enums.RoleList;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;

import java.util.Collection;


@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @NotNull
    @NaturalId
    @Column(unique = true)
    private String userName;
    @NotNull
    private String email;
    @NotNull
    String password;
    String spotifyID;
    String profileImageURL;
    Integer score;
    Integer streak;
    boolean enabled;
    boolean tokenExpired;
    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;



    public User() {
    }

    public void addScore(int scoreToAdd){
        this.score += scoreToAdd;
    }

    public void addStreak(int streakS){
        if (streakS>this.streak){
            streak = streakS;
        }
    }

}