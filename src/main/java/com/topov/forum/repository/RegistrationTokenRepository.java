package com.topov.forum.repository;

import com.topov.forum.token.RegistrationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationTokenRepository extends JpaRepository<RegistrationToken, Long> {
}
