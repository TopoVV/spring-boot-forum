package com.topov.forum.repository;

import com.topov.forum.model.ViewCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ViewCounterRepository extends JpaRepository<ViewCounter, Long> {
    @Query("SELECT vc FROM ViewCounter vc WHERE vc.post.postId = :postId")
    Optional<ViewCounter> findByPostId(Long postId);
}
