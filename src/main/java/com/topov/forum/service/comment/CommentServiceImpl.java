package com.topov.forum.service.comment;

import com.topov.forum.dto.error.Error;
import com.topov.forum.dto.error.ValidationError;
import com.topov.forum.dto.model.CommentDto;
import com.topov.forum.dto.request.comment.CommentCreateRequest;
import com.topov.forum.dto.request.comment.CommentEditRequest;
import com.topov.forum.dto.result.comment.CommentCreateResult;
import com.topov.forum.dto.result.comment.CommentDeleteResult;
import com.topov.forum.dto.result.comment.CommentEditResult;
import com.topov.forum.dto.result.comment.CommentGetAllResult;
import com.topov.forum.exception.CommentException;
import com.topov.forum.mapper.CommentMapper;
import com.topov.forum.model.Comment;
import com.topov.forum.model.ForumUser;
import com.topov.forum.model.Post;
import com.topov.forum.repository.CommentRepository;
import com.topov.forum.security.AuthenticationService;
import com.topov.forum.service.post.PostService;
import com.topov.forum.service.user.UserService;
import com.topov.forum.validation.ValidationResult;
import com.topov.forum.validation.comment.CommentValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Log4j2
@Service
public class CommentServiceImpl implements CommentService {
    private final AuthenticationService authenticationService;
    private final CommentRepository commentRepository;
    private final CommentValidator commentValidator;
    private final CommentMapper commentMapper;

    private final UserService userService;
    private final PostService postService;


    @Autowired
    public CommentServiceImpl(AuthenticationService authenticationService,
                              CommentRepository commentRepository,
                              CommentValidator commentValidator,
                              CommentMapper commentMapper,
                              UserService userService,
                              PostService postService) {
        this.authenticationService = authenticationService;
        this.commentRepository = commentRepository;
        this.commentValidator = commentValidator;
        this.commentMapper = commentMapper;
        this.userService = userService;
        this.postService = postService;
    }

    @Override
    @Transactional
    public CommentGetAllResult getAllComments(Long postId, Pageable pageable) {
        final ValidationResult validationResult = commentValidator.validatePostExists(postId);
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
            log.error("Error creating comment", e);
            throw new CommentException("Cannot create comment", e);
        }
    }

    @Override
    @Transactional
    @PreAuthorize("@commentServiceSecurity.checkOwnership(#commentId) or hasRole('SUPERUSER')")
    public CommentEditResult editComment(Long commentId, CommentEditRequest commentEditRequest) {
        log.debug("Editing comment: {}", commentEditRequest);
        try {

            final Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found"));

            comment.setText(commentEditRequest.getNewText());
            final CommentDto commentDto = commentMapper.toDto(comment);

            return new CommentEditResult(HttpStatus.OK, "The comment has been edited", commentDto);

        } catch (ResponseStatusException e) {
            throw e;
        } catch (RuntimeException e) {
            log.error("Cannot edit comment", e);
            throw new CommentException("Cannot edit comment", e);
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
