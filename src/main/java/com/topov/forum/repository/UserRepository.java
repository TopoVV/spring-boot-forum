package com.topov.forum.repository;

import com.topov.forum.model.ForumUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface ForumUserRepository extends JpaRepository<ForumUser, Long> {
    Optional<ForumUser> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
