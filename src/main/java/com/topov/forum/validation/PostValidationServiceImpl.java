package com.topov.forum.validation;

import com.topov.forum.dto.request.post.PostCreateRequest;
import com.topov.forum.dto.request.post.PostEditRequest;
import com.topov.forum.repository.PostRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class PostValidationServiceImpl implements PostValidationService {
    private final PostRepository postRepository;

    @Autowired
    public PostValidationServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public ValidationResult validateCreatePost(PostCreateRequest postCreateRequest) {
        log.debug("Post creation request validation");
        List<ValidationError> errors = new ArrayList<>();
        if(postRepository.existsByTitle(postCreateRequest.getTitle())) {
            final ValidationError error = new ValidationError("title", "Post with the same title already exists");
            errors.add(error);
        }
        return new ValidationResult(errors);
    }

    @Override
    public ValidationResult validateEditPost(PostEditRequest postEditRequest) {
        log.debug("Validating post edition request");
        List<ValidationError> errors = new ArrayList<>();
        final String newTitle = postEditRequest.getNewTitle();
        if(!postEditRequest.getOldTitle().equals(newTitle)) {
            if(postRepository.existsByTitle(newTitle)) {
                final ValidationError error = new ValidationError("title", "Post with the same title already exists");
                errors.add(error);
            }
        }
        return new ValidationResult(errors);
    }


}
