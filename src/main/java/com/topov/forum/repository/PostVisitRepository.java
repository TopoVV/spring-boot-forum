package com.topov.forum.repository;

import com.topov.forum.model.PostVisit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostVisitRepository extends JpaRepository<PostVisit, Long> {
}
