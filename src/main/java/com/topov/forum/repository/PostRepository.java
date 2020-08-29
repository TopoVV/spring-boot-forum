package com.topov.forum.repository;

import com.topov.forum.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    boolean existsByTitle(String title);
    @Query("SELECT p FROM Post p WHERE p.postId = :postId AND p.status = 'ACTIVE'")
    Optional<Post> findActiveById(Long postId);
}
