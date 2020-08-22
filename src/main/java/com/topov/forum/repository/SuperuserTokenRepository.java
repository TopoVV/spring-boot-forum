package com.topov.forum.repository;

import com.topov.forum.token.SuperuserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SuperuserTokenRepository extends JpaRepository<SuperuserToken, Long> {
    @Query("SELECT t FROM SuperuserToken t WHERE t.token = :token")
    Optional<SuperuserToken> findTokenByTokenValue(String token);
}
