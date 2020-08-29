package com.topov.forum.service;

import com.topov.forum.model.PostVisit;
import com.topov.forum.repository.PostVisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VisitServiceImpl implements VisitService {
    private final PostVisitRepository postVisitRepository;

    @Autowired
    public VisitServiceImpl(PostVisitRepository postVisitRepository) {
        this.postVisitRepository = postVisitRepository;
    }

    @Override
    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void postVisited(PostVisit visit) {
        postVisitRepository.save(visit);
    }
}
