package com.topov.forum.service.comment;

import com.topov.forum.dto.error.Error;
import com.topov.forum.dto.model.CommentDto;
import com.topov.forum.dto.request.comment.CommentCreateRequest;
import com.topov.forum.dto.request.comment.CommentEditRequest;
import com.topov.forum.dto.result.comment.CommentCreateResult;
import com.topov.forum.dto.result.comment.CommentDeleteResult;
import com.topov.forum.dto.result.comment.CommentEditResult;
import com.topov.forum.dto.result.comment.CommentGetAllResult;
import com.topov.forum.mapper.CommentMapper;
import com.topov.forum.model.Comment;
import com.topov.forum.model.ForumUser;
import com.topov.forum.model.Post;
import com.topov.forum.repository.CommentRepository;
import com.topov.forum.security.AuthenticationService;
import com.topov.forum.service.post.PostService;
import com.topov.forum.service.user.UserService;
import com.topov.forum.validation.ValidationResult;
import com.topov.forum.validation.ValidationService;
import com.topov.forum.validation.comment.rule.CommentCreateValidation;
import com.topov.forum.validation.comment.rule.CommentEditValidation;
import com.topov.forum.validation.comment.rule.CommentsGetAllValidation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityExistsException;
import java.util.List;

@Log4j2
@Service
public class CommentServiceImpl implements CommentService {
    private final AuthenticationService authenticationService;
    private final CommentRepository commentRepository;
    private final ValidationService validationService;
    private final CommentMapper commentMapper;

    private final UserService userService;
    private final PostService postService;


    @Autowired
    public CommentServiceImpl(AuthenticationService authenticationService,
                              CommentRepository commentRepository,
                              ValidationService validationService,
                              CommentMapper commentMapper,
                              UserService userService,
                              PostService postService) {
        this.authenticationService = authenticationService;
        this.commentRepository = commentRepository;
        this.validationService = validationService;
        this.commentMapper = commentMapper;
        this.userService = userService;
        this.postService = postService;
    }

    @Override
    @Transactional
    public CommentGetAllResult getAllComments(Long postId, Pageable pageable) {
        final CommentsGetAllValidation validationRule = new CommentsGetAllValidation(postId);
        final ValidationResult validationResult = validationService.validate(validationRule);
        if (validationResult.containsErrors()) {
            final List<Error> errors = validationResult.getValidationErrors();
            return new CommentGetAllResult(HttpStatus.BAD_REQUEST, errors, "Error getting comments");
        }

        final Page<CommentDto> comments = commentRepository.findCommentsForPost(postId, pageable)
            .map(commentMapper::toDto);

        return new CommentGetAllResult(HttpStatus.OK, comments);
    }

    @Override
    @Transactional
    public CommentCreateResult createComment(Long postId, CommentCreateRequest commentCreateRequest) {
        log.debug("Creating comment: {}", commentCreateRequest);
        try {
            final CommentCreateValidation validationRule = new CommentCreateValidation(postId);
            final ValidationResult validationResult = validationService.validate(validationRule);
            if (validationResult.containsErrors()) {
                final List<Error> validationErrors = validationResult.getValidationErrors();
                return new CommentCreateResult(HttpStatus.CREATED, validationErrors, "Comment cannot be saved");
            }

            final Comment newComment = new Comment();
            newComment.setText(commentCreateRequest.getText());

            final Long creatorId = authenticationService.getCurrentUserId();
            final ForumUser creator = userService.findUser(creatorId);
            final Post post = postService.findPost(postId);

            creator.addComment(newComment);
            post.addComment(newComment);

            final Comment savedComment = commentRepository.save(newComment);
            final CommentDto commentDto = commentMapper.toDto(savedComment);

            return new CommentCreateResult(HttpStatus.CREATED, "The comment has been saved", commentDto);
        } catch (RuntimeException e) {
            log.error("Comment creation error");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    @Transactional
    @PreAuthorize("@commentServiceSecurity.checkOwnership(#commentId) or hasRole('SUPERUSER')")
    public CommentEditResult editComment(Long commentId, CommentEditRequest commentEditRequest) {
        log.debug("Editing comment: {}", commentEditRequest);
        try {
            final CommentEditValidation validationRule = new CommentEditValidation(commentId);
            final ValidationResult validationResult = validationService.validate(validationRule);
            if (validationResult.containsErrors()) {
                final List<Error> errors = validationResult.getValidationErrors();
                return new CommentEditResult(HttpStatus.BAD_REQUEST, errors, "The comment cannot be edited");
            }

            final Comment comment = commentRepository.findById(commentId)
                .orElseThrow(EntityExistsException::new);

            comment.setText(commentEditRequest.getNewText());
            final CommentDto commentDto = commentMapper.toDto(comment);

            return new CommentEditResult(HttpStatus.OK, "The comment has been edited", commentDto);

        } catch (RuntimeException e) {
            log.error("Comment modification error", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    @Transactional
    @PreAuthorize("@commentServiceSecurity.checkOwnership(#commentId) or hasRole('SUPERUSER')")
    public CommentDeleteResult deleteComment(Long commentId) {
        log.debug("Deleting comment with id={}", commentId);
        if (commentRepository.existsById(commentId)) {
            commentRepository.deleteById(commentId);
            return new CommentDeleteResult(HttpStatus.OK, "The comment has been deleted");
        } else {
            final Error error = new Error("Comment not found");
            return new CommentDeleteResult(HttpStatus.NOT_FOUND, error, "Cannot delete comment");
        }
    }
}
