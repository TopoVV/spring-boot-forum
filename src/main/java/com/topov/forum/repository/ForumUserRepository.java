package com.topov.forum.repository;

import com.topov.forum.model.ForumUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForumUserRepository extends JpaRepository<ForumUser, Long> {
}
