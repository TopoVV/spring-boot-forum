package com.topov.forum.validation.post;

<<<<<<< HEAD
import com.topov.forum.dto.error.Error;
import com.topov.forum.dto.request.post.PostEditRequest;
import com.topov.forum.repository.PostRepository;
import com.topov.forum.dto.error.ValidationError;
=======
import com.topov.forum.dto.request.post.PostEditRequest;
import com.topov.forum.repository.PostRepository;
import com.topov.forum.validation.ValidationError;
>>>>>>> 282c7c2d0776712c32790097da1739769a0824f5
import com.topov.forum.validation.ValidationResult;
import com.topov.forum.validation.Validator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class PostEditValidator implements Validator<PostEditRequest> {
    private final PostRepository postRepository;

    @Autowired
    public PostEditValidator(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public ValidationResult validate(PostEditRequest data) {
        log.debug("Validating post edition request");
<<<<<<< HEAD
        List<Error> errors = new ArrayList<>();
        final String newTitle = data.getNewTitle();
        if(!data.getOldTitle().equals(newTitle)) {
            if(postRepository.existsByTitle(newTitle)) {
                final Error error = new ValidationError("title", "Post with the same title already exists");
=======
        List<ValidationError> errors = new ArrayList<>();
        final String newTitle = data.getNewTitle();
        if(!data.getOldTitle().equals(newTitle)) {
            if(postRepository.existsByTitle(newTitle)) {
                final ValidationError error = new ValidationError("title", "Post with the same title already exists");
>>>>>>> 282c7c2d0776712c32790097da1739769a0824f5
                errors.add(error);
            }
        }
        return new ValidationResult(errors);
    }
}
