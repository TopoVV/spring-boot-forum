package com.topov.forum.repository;

import com.topov.forum.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

<<<<<<<HEAD
=======
    >>>>>>>tmp-1

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
<<<<<<< HEAD
    @Query("SELECT c FROM Comment c WHERE c.commentId = :commentId AND c.status = 'ACTIVE'")
    Optional<Comment> findActiveById(Long commentId);
=======
    @Query(value = "SELECT c FROM Comment c WHERE c.post.postId = :postId")
    Page<Comment> findCommentsForPost(Long postId, Pageable pageable);
>>>>>>> tmp-1
}
