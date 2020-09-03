package com.topov.forum.repository;

import com.topov.forum.token.AccountConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountConfirmationTokenRepository extends JpaRepository<AccountConfirmationToken, Long> {
    @Query("SELECT t FROM AccountConfirmationToken t WHERE t.tokenValue = :token")
    Optional<AccountConfirmationToken> findTokenByTokenValue(String token);
}
