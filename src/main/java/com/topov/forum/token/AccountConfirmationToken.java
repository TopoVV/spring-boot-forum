package com.topov.forum.token;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "registration_tokens")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AccountConfirmationToken extends Token {
    private static final int DEFAULT_LIFE_TIME_MINUTES = 5;
    private String username;

    @Version
    private long version;

    public AccountConfirmationToken(String forUser) {
        this.creationTime = LocalDateTime.now();
        this.tokenValue = UUID.randomUUID().toString();
        this.isEnabled = true;
        this.username = forUser;
    }

    @Override
    public boolean isTokenValid() {
        final long timeLived = Duration.between(creationTime, LocalDateTime.now())
            .toMinutes();
        return isEnabled && timeLived < DEFAULT_LIFE_TIME_MINUTES;
    }
}
