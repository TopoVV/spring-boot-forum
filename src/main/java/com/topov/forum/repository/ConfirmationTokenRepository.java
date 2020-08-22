package com.topov.forum.repository;

import com.topov.forum.token.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
    @Query("SELECT t FROM ConfirmationToken t WHERE t.token = :token")
    Optional<ConfirmationToken> findTokenByTokenValue(String token);
}
