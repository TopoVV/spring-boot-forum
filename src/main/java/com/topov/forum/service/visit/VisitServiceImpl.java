package com.topov.forum.service.visit;

import com.topov.forum.model.PostVisit;
import com.topov.forum.repository.PostVisitRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
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
        try {
            postVisitRepository.save(visit);
        } catch (RuntimeException e) {
            log.warn("Post visit not saved");
        }
    }
}
