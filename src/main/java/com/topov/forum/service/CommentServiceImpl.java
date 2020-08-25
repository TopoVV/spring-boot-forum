package com.topov.forum.service;

import com.topov.forum.dto.CommentDto;
import com.topov.forum.dto.request.CreateCommentRequest;
import com.topov.forum.dto.request.CreatePostRequest;
import com.topov.forum.dto.response.CreateCommentResponse;
import com.topov.forum.mapper.CommentMapper;
import com.topov.forum.model.Comment;
import com.topov.forum.model.Status;
import com.topov.forum.repository.CommentRepository;
import com.topov.forum.repository.PostRepository;
import com.topov.forum.repository.UserRepository;
import com.topov.forum.security.AuthenticationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Log4j2
@Service
public class CommentServiceImpl implements CommentService {
    private final AuthenticationService authenticationService;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentMapper commentMapper;

    @Autowired
    public CommentServiceImpl(AuthenticationService authenticationService, CommentRepository commentRepository, UserRepository repository, PostRepository postRepository, CommentMapper commentMapper) {
        this.authenticationService = authenticationService;
        this.commentRepository = commentRepository;
        this.userRepository = repository;
        this.postRepository = postRepository;
        this.commentMapper = commentMapper;
    }

    @Override
    @Transactional
    public CreateCommentResponse createComment(CreateCommentRequest createCommentRequest) {
        log.debug("Creating comment: {}", createCommentRequest);
        try {
            final Comment newComment = assembleComment(createCommentRequest);
            final String username = authenticationService.getAuthenticatedUser().getUsername();
            addCommentToUser(newComment, username);
            addCommentToPost(createCommentRequest, newComment);
            commentRepository.flush();
            final CommentDto commentDto = commentMapper.toDto(newComment);
            return new CreateCommentResponse(commentDto);
        } catch (Exception e) {
            log.error("Error creating comment", e);
            throw e;
        }
    }

    private void addCommentToUser(Comment newComment, String username) {
        userRepository.findByUsername(username)
            .stream()
            .peek(forumUser -> forumUser.addComment(newComment))
            .findFirst()
            .orElseThrow(() -> {
                log.error("User not found");
                return new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            });
    }

    private void addCommentToPost(CreateCommentRequest createCommentRequest, Comment newComment) {
        postRepository.findById(createCommentRequest.getPostId())
            .stream()
            .peek(post -> post.addComment(newComment))
            .findFirst()
            .orElseThrow(() -> {
                log.error("Post not found");
                return new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
            });
    }

    private Comment assembleComment(CreateCommentRequest createPostRequest) {
        final Comment comment = new Comment();
        comment.setText(createPostRequest.getText());
        comment.setStatus(Status.ACTIVE);
        return comment;
    }
}
