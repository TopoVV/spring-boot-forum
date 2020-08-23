package com.topov.forum.repository;

import com.topov.forum.model.ForumUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<ForumUser, Long> {
    Optional<ForumUser> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    @Query("SELECT u FROM ForumUser u JOIN FETCH u.roles WHERE u.username = :username")
    Optional<ForumUser> findByUsernameWithRoles(String username);
}
