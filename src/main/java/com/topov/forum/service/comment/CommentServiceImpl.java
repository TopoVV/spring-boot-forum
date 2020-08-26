package com.topov.forum.service.comment;

import com.topov.forum.dto.CommentDto;
import com.topov.forum.dto.request.CreateCommentRequest;
import com.topov.forum.dto.request.EditCommentRequest;
import com.topov.forum.dto.response.CreateCommentResponse;
import com.topov.forum.dto.response.EditCommentResponse;
import com.topov.forum.exception.CommentException;
import com.topov.forum.mapper.CommentMapper;
import com.topov.forum.model.Comment;
import com.topov.forum.model.Status;
import com.topov.forum.repository.CommentRepository;
import com.topov.forum.security.AuthenticationService;
import com.topov.forum.service.interraction.AddComment;
import com.topov.forum.service.post.PostServiceInternal;
import com.topov.forum.service.user.UserServiceInternal;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Log4j2
@Service
public class CommentServiceImpl implements CommentService {
    private final AuthenticationService authenticationService;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    private final UserServiceInternal userService;
    private final PostServiceInternal postService;

    @Autowired
    public CommentServiceImpl(AuthenticationService authenticationService,
                              CommentRepository commentRepository,
                              CommentMapper commentMapper,
                              UserServiceInternal userService,
                              PostServiceInternal postService) {
        this.authenticationService = authenticationService;
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.userService = userService;
        this.postService = postService;
    }

    @Override
    @Transactional
    public CreateCommentResponse createComment(CreateCommentRequest createCommentRequest) {
        log.debug("Creating comment: {}", createCommentRequest);
        try {
            final Comment newComment = assembleComment(createCommentRequest);
            final Long currentUserId = authenticationService.getCurrentUserId();

            postService.addComment(new AddComment(createCommentRequest.getPostId(), newComment));
            userService.addComment(new AddComment(currentUserId, newComment));
            commentRepository.flush();

            final CommentDto commentDto = commentMapper.toDto(newComment);
            return new CreateCommentResponse(commentDto);
        } catch (RuntimeException e) {
            log.error("Error creating comment", e);
            throw new CommentException("Cannot create comment", e);
        }
    }

    @Override
    @Transactional
    @PreAuthorize("@commentServiceSecurity.checkOwnership(#editCommentRequest.commentId) or hasRole('SUPERUSER')")
    public EditCommentResponse editComment(EditCommentRequest editCommentRequest) {
        final Long commentId = editCommentRequest.getCommentId();

        return commentRepository.findById(commentId)
            .map(comment -> doEditComment(editCommentRequest, comment))
            .map(commentMapper::toDto)
            .map(EditCommentResponse::new)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found"));
    }

    private Comment doEditComment(EditCommentRequest editCommentRequest, Comment comment) {
        comment.setText(editCommentRequest.getText());
        return comment;
    }

    private Comment assembleComment(CreateCommentRequest createPostRequest) {
        final Comment comment = new Comment();
        comment.setText(createPostRequest.getText());
        comment.setStatus(Status.ACTIVE);
        return comment;
    }
}
