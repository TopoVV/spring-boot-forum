package com.topov.forum.token;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "registration_tokens")
@SequenceGenerator(name = "registration_token_id_seq", allocationSize = 1)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "registration_token_id_seq")
    @Column(name = "token_id")
    private Long tokenId;
    @Column(name = "token")
    private String token;
    @Column(name = "username")
    private String username;
    @Column(name = "created_at")
    private LocalDateTime creationTime;
    private Boolean isUsed;

    public RegistrationToken(String forUser) {
        this.creationTime = LocalDateTime.now();
        this.token = UUID.randomUUID().toString();
        this.isUsed = false;
        this.username = forUser;
    }

    public boolean isTokenValid() {
        if (!isUsed || Duration.between(creationTime, LocalDateTime.now()).toMinutes() < 5) {
            this.isUsed = true;
            return true;
        }
       return false;
    }
}
