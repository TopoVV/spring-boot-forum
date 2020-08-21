package com.topov.forum.repository;

import com.topov.forum.token.RegistrationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegistrationTokenRepository extends JpaRepository<RegistrationToken, Long> {
    @Query("SELECT t FROM RegistrationToken t WHERE t.token = :token")
    Optional<RegistrationToken> findTokenByTokenValue(String token);
}
