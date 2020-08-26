package com.topov.forum.service;

import com.topov.forum.model.ViewCounter;
import com.topov.forum.repository.ViewCounterRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Log4j2
@Service
public class ViewCounterServiceImpl implements ViewCounterService {
    private final ViewCounterRepository viewCounterRepository;

    @Autowired
    public ViewCounterServiceImpl(ViewCounterRepository viewCounterRepository) {
        this.viewCounterRepository = viewCounterRepository;
    }

    @Async
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void postViewed(Long postId) {
        try {
            final Optional<ViewCounter> byPostId = viewCounterRepository.findByPostId(postId);
            byPostId
                .orElseThrow(EntityNotFoundException::new)
                .increment();
        } catch (EntityNotFoundException e) {
            log.warn("PostView.increment() failed");
        }
    }

}
