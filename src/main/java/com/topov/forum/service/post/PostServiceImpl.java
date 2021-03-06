package com.topov.forum.service.post;

import com.topov.forum.dto.error.Error;
import com.topov.forum.dto.model.post.PostDto;
import com.topov.forum.dto.request.post.PostCreateRequest;
import com.topov.forum.dto.request.post.PostEditRequest;
import com.topov.forum.dto.result.post.*;
import com.topov.forum.mapper.PostMapper;
import com.topov.forum.model.ForumUser;
import com.topov.forum.model.Post;
import com.topov.forum.model.PostVisit;
import com.topov.forum.repository.PostRepository;
import com.topov.forum.security.AuthenticationService;
import com.topov.forum.service.user.UserService;
import com.topov.forum.service.visit.VisitService;
import com.topov.forum.validation.ValidationResult;
import com.topov.forum.validation.ValidationService;
import com.topov.forum.validation.post.rule.PostCreateValidationRule;
import com.topov.forum.validation.post.rule.PostEditValidationRule;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.List;



@Log4j2
@Service
public class PostServiceImpl implements PostService {
    private static final String POST_URI_TEMPLATE = "http://localhost:8080/posts/%d";

    private final AuthenticationService authenticatedUserService;
    private final PostRepository postRepository;
    private final ValidationService validationService;
    private final VisitService visitService;
    private final UserService userService;
    private final PostMapper postMapper;

    @Autowired
    public PostServiceImpl(AuthenticationService authenticatedUserService,
                           ValidationService validationService,
                           PostRepository postRepository,
                           VisitService visitService,
                           UserService userService,
                           PostMapper postMapper) {
        this.authenticatedUserService = authenticatedUserService;
        this.validationService = validationService;
        this.postRepository = postRepository;
        this.visitService = visitService;
        this.userService = userService;
        this.postMapper = postMapper;
    }

    @Override
    @Transactional
    public PostGetResult getPost(Long postId) {
        final Post post = postRepository.findById(postId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));

        final Long currentUserId = authenticatedUserService.getCurrentUserId();
        final PostDto postDto = postMapper.toDto(post);
        visitService.postVisited(new PostVisit(currentUserId, postId));
        return new PostGetResult(HttpStatus.OK, postDto);
    }

    @Override
    @Transactional
    public PostGetAllResult getAllPosts(Pageable pageable) {
        final Page<PostDto> posts = postRepository.findAll(pageable)
            .map(postMapper::toDto);
        return new PostGetAllResult(HttpStatus.OK, posts);
    }

    @Override
    @Transactional
    public PostCreateResult createPost(PostCreateRequest postCreateRequest) {
        log.debug("Creating a post: {}", postCreateRequest);
        try {
            final PostCreateValidationRule validationRule = new PostCreateValidationRule(postCreateRequest);
            final ValidationResult validationResult = validationService.validate(validationRule);
            if (validationResult.containsErrors()) {
                final List<Error> errors = validationResult.getValidationErrors();
                return new PostCreateResult(HttpStatus.BAD_REQUEST, errors, "Post cannot be created");
            }


            final Post newPost = new Post();
            newPost.setTitle(postCreateRequest.getTitle());
            newPost.setText(postCreateRequest.getText());

            final Long creatorId = authenticatedUserService.getCurrentUserId();
            final ForumUser creator = userService.findUser(creatorId);

            creator.addPost(newPost);

            final Post savedPost = postRepository.save(newPost);
            final PostDto postDto = postMapper.toDto(savedPost);
            final URI location = buildCreatedPostLocation(postDto.getPostId());
            return new PostCreateResult(HttpStatus.CREATED, "The post has been saved", location, postDto);
        } catch (RuntimeException e) {
            log.error("Cannot create post", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot create post", e);
        }
    }

    private URI buildCreatedPostLocation(Long postId) {
        final String location = String.format(POST_URI_TEMPLATE, postId);
        return URI.create(location);
    }

    @Override
    @Transactional
    @PreAuthorize("@postServiceSecurity.checkOwnership(#postId) or hasRole('SUPERUSER')")
    public PostEditResult editPost(Long postId, PostEditRequest postEditRequest) {
        log.debug("Editing post: {}", postEditRequest);
        try {
            final PostEditValidationRule validationRule = new PostEditValidationRule(postId, postEditRequest);
            final ValidationResult validationResult = validationService.validate(validationRule);
            if (validationResult.containsErrors()) {
                final List<Error> errors = validationResult.getValidationErrors();
                return new PostEditResult(HttpStatus.BAD_REQUEST, errors, "Post cannot be edited");
            }

            final Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));

            post.setTitle(postEditRequest.getNewTitle());
            post.setText(postEditRequest.getText());

            final PostDto postDto = postMapper.toDto(post);
            return new PostEditResult(HttpStatus.OK, "Post has been successfully edited", postDto);

        } catch (ResponseStatusException e) {
            throw e;
        } catch (RuntimeException e) {
            log.error("Cannot edit post", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot edit post", e);
        }
    }

    @Override
    @Transactional
    @PreAuthorize("@postServiceSecurity.checkOwnership(#postId) or hasRole('SUPERUSER')")
    public PostDeleteResult deletePost(Long postId) {
        log.debug("Deleting post with id={}", postId);
        if (postRepository.existsById(postId)) {
            postRepository.deleteById(postId);
           return new PostDeleteResult(HttpStatus.OK, "Post deleted");
        } else {
            final Error error = new Error("Post nof found");
            return new PostDeleteResult(HttpStatus.NOT_FOUND, error, "Cannot delete post");
        }
    }

    @Override
    public Post findPost(Long postId) {
        return postRepository.findById(postId)
            .orElseThrow(EntityNotFoundException::new);
    }
}
