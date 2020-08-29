package com.topov.forum.repository;

import com.topov.forum.model.Comment;
import com.topov.forum.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c WHERE c.commentId = :commentId AND c.status = 'ACTIVE'")
    Optional<Comment> findActiveById(Long commentId);
}
