package com.parallax.backend.entity;


import com.parallax.backend.utils.ROLE;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Table(name = "Users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String spotifyID;
    private String profileImageURL;
    private Integer score;
    private Integer streak;
    private ROLE role;

    @Transient
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User(UserDTO userDTO) {
        this.username = userDTO.username();
        this.password = encodePassword(userDTO.password());
        this.score = 0;
        this.streak = 0;
    }

    public void changePassword(String newPassword) {
        this.password = passwordEncoder.encode(newPassword);
    }

    public String encodePassword(String pass) {
        return passwordEncoder.encode(pass);
    }

    public boolean decodePassword(String passwords){
        return passwordEncoder.matches(passwords, password);
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
