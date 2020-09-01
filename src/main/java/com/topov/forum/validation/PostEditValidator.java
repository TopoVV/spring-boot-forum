package com.topov.forum.validation;

import com.topov.forum.dto.request.post.PostEditRequest;
import com.topov.forum.repository.PostRepository;
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
        List<ValidationError> errors = new ArrayList<>();
        final String newTitle = data.getNewTitle();
        if(!data.getOldTitle().equals(newTitle)) {
            if(postRepository.existsByTitle(newTitle)) {
                final ValidationError error = new ValidationError("title", "Post with the same title already exists");
                errors.add(error);
            }
        }
        return new ValidationResult(errors);
    }
}
