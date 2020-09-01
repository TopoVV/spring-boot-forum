package com.topov.forum.validation.post;

import com.topov.forum.dto.request.post.PostCreateRequest;
import com.topov.forum.repository.PostRepository;
import com.topov.forum.validation.ValidationError;
import com.topov.forum.validation.ValidationResult;
import com.topov.forum.validation.Validator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class PostCreateValidator implements Validator<PostCreateRequest> {
    private final PostRepository postRepository;

    @Autowired
    public PostCreateValidator(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public ValidationResult validate(PostCreateRequest data) {
        log.debug("Post creation request validation");
        List<ValidationError> errors = new ArrayList<>();
        if(postRepository.existsByTitle(data.getTitle())) {
            final ValidationError error = new ValidationError("title", "Post with the same title already exists");
            errors.add(error);
        }
        return new ValidationResult(errors);
    }
}
