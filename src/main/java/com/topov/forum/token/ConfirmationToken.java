package com.topov.forum.token;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "registration_tokens")
@SequenceGenerator(name = "token_id_gen", sequenceName = "registration_token_id_seq", allocationSize = 1)
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ConfirmationToken extends Token {
    private static final int DEFAULT_LIFE_TIME_MINUTES = 5;
    private String username;

    public ConfirmationToken(String forUser) {
        this.creationTime = LocalDateTime.now();
        this.token = UUID.randomUUID().toString();
        this.isEnabled = true;
        this.username = forUser;
    }

    @Override
    public boolean isTokenValid() {
        final long timeLived = Duration.between(creationTime, LocalDateTime.now()).toMinutes();
        return isEnabled && timeLived < DEFAULT_LIFE_TIME_MINUTES;
    }
}
