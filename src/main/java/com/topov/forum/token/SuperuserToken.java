package com.topov.forum.token;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "superuser_tokens")
@SequenceGenerator(name = "token_id_gen", sequenceName = "superuser_token_id_seq", allocationSize = 1)
@Data
@EqualsAndHashCode(callSuper = true)
public class SuperuserToken extends Token {
    private static final int DEFAULT_LIFE_TIME_MINUTES = 10;

    public SuperuserToken() {
        this.creationTime = LocalDateTime.now();
        this.token = UUID.randomUUID().toString();
        this.isUsed = false;
    }

    @Override
    public boolean isTokenValid() {
        final long timeLived = Duration.between(creationTime, LocalDateTime.now()).toMinutes();
        if (!isUsed && timeLived < DEFAULT_LIFE_TIME_MINUTES) {
            this.isUsed = true;
            return true;
        }
        return false;
    }
}
