package com.topov.forum.service.comment;

import com.topov.forum.dto.request.comment.CommentCreateRequest;
import com.topov.forum.dto.response.comment.CommentDeleteResponse;
import com.topov.forum.dto.response.post.PostDeleteResponse;
import com.topov.forum.model.Post;
import com.topov.forum.service.data.CommentCreateData;
import com.topov.forum.service.data.CommentEditData;
import com.topov.forum.dto.CommentDto;
import com.topov.forum.dto.response.comment.CommentCreateResponse;
import com.topov.forum.dto.response.comment.CommentEditResponse;
import com.topov.forum.exception.CommentException;
import com.topov.forum.mapper.CommentMapper;
import com.topov.forum.model.Comment;
import com.topov.forum.model.Status;
import com.topov.forum.repository.CommentRepository;
import com.topov.forum.security.AuthenticationService;
import com.topov.forum.service.post.PostServiceInternal;
import com.topov.forum.service.user.UserServiceInternal;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityExistsException;

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
    public CommentCreateResponse createComment(Long targetPostId, CommentCreateRequest createRequest) {
        log.debug("Creating comment: {}", createRequest);
        try {
            final Comment newComment = new Comment();
            newComment.setText(createRequest.getText());
            newComment.setStatus(Status.ACTIVE);

            final Long creatorId = authenticationService.getCurrentUserId();

            postService.addComment(targetPostId, newComment);
            userService.addComment(creatorId, newComment);
            commentRepository.flush();
            final CommentDto commentDto = commentMapper.toDto(newComment);
            return new CommentCreateResponse(commentDto);
        } catch (RuntimeException e) {
            log.error("Error creating comment", e);
            throw new CommentException("Cannot create comment", e);
        }
    }

    @Override
    @Transactional
    @PreAuthorize("@commentServiceSecurity.checkOwnership(#editData.commentId) or hasRole('SUPERUSER')")
    public CommentEditResponse editComment(CommentEditData editData) {
        try {
            final Long commentId = editData.getCommentId();
            final Comment comment = commentRepository.findActiveById(commentId).orElseThrow(EntityExistsException::new);
            comment.setText(editData.getNewText());
            final CommentDto commentDto = commentMapper.toDto(comment);
            return new CommentEditResponse(commentDto);
        } catch (EntityExistsException e) {
            log.error("Comment not found when edit");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found");
        }
    }

    @Override
    @Transactional
    @PreAuthorize("@commentServiceSecurity.checkOwnership(#commentId) or hasRole('SUPERUSER')")
    public CommentDeleteResponse deleteComment(Long commentId) {
        log.debug("Deleting comment with id={}", commentId);
        try {
            final Comment comment = commentRepository.findActiveById(commentId).orElseThrow(EntityExistsException::new);
            comment.disable();
            return CommentDeleteResponse.deleted();
        } catch (EntityExistsException e) {
            log.error("Comment not found when delete");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }
    }
}
