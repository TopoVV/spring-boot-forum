package com.topov.forum.token;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@MappedSuperclass
public abstract class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    protected Long tokenId;
    @Column(name = "token")
    protected String token;
    @Column(name = "created_at")
    protected LocalDateTime creationTime;
    @Column(name = "is_enabled")
    protected Boolean isEnabled;

    public void revoke() {
        this.isEnabled = false;
    }

    public abstract boolean isTokenValid();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Token token1 = (Token) o;
        return token.equals(token1.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token);
    }
}
