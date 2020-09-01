package com.topov.forum.service.post;

import com.topov.forum.dto.PostDto;
import com.topov.forum.dto.ShortPostDto;
import com.topov.forum.dto.request.post.PostCreateRequest;
import com.topov.forum.dto.request.post.PostEditRequest;
import com.topov.forum.dto.response.post.PostCreateResponse;
import com.topov.forum.dto.response.post.PostDeleteResponse;
import com.topov.forum.dto.response.post.PostEditResponse;
import com.topov.forum.exception.PostException;
import com.topov.forum.mapper.PostMapper;
import com.topov.forum.model.*;
import com.topov.forum.repository.PostRepository;
import com.topov.forum.security.AuthenticationService;
import com.topov.forum.service.VisitService;
import com.topov.forum.service.data.PostEditData;
import com.topov.forum.service.user.UserService;
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

<<<<<<<HEAD
=======
    >>>>>>>tmp-1
    <<<<<<<HEAD
=======
    >>>>>>>tmp-1

@Log4j2
@Service
public class PostServiceImpl implements PostService {
    private final AuthenticationService authenticatedUserService;
    private final PostRepository postRepository;
    private final VisitService visitService;
    private final UserService userService;
    private final PostMapper postMapper;

    @Autowired
    public PostServiceImpl(AuthenticationService authenticatedUserService,
                           PostRepository postRepository,
                           VisitService visitService,
                           UserService userService,
                           PostMapper postMapper) {
        this.authenticatedUserService = authenticatedUserService;
        this.postRepository = postRepository;
        this.visitService = visitService;
        this.userService = userService;
        this.postMapper = postMapper;
    }

    @Override
    @Transactional
    public PostDto getPost(Long postId) {
<<<<<<< HEAD
        try {
            final Post post = postRepository.findById(postId).orElseThrow(EntityNotFoundException::new);
            final Long currentUserId = authenticatedUserService.getCurrentUserId();
            final PostDto postDto = postMapper.toDto(post);
            visitService.postVisited(new PostVisit(currentUserId, postId));
            return postDto;
        } catch (EntityNotFoundException e) {
            log.debug("Post not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }
=======
        final Post post = postRepository.findById(postId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));

        final Long currentUserId = authenticatedUserService.getCurrentUserId();
        final PostDto postDto = postMapper.toDto(post);
        visitService.postVisited(new PostVisit(currentUserId, postId));
        return postDto;
>>>>>>> tmp-1
    }

    @Override
    @Transactional
    public Page<ShortPostDto> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable)
            .map(postMapper::toShortDto);
    }

    @Override
    @Transactional
    public PostCreateResponse createPost(PostCreateRequest createRequest) {
        log.debug("Creating a post: {}", createRequest);
        try {
            final Post newPost = new Post();
<<<<<<< HEAD
            newPost.setTitle(createRequest.getTitle());
            newPost.setText(createRequest.getText());
            newPost.setStatus(Status.ACTIVE);
            final Long creatorId = authenticatedUserService.getCurrentUserId();
            userService.addPost(creatorId, newPost);
            postRepository.save(newPost);
            final PostDto postDto = postMapper.toDto(newPost);
=======
            newPost.setTitle(postCreateRequest.getTitle());
            newPost.setText(postCreateRequest.getText());

            final Long creatorId = authenticatedUserService.getCurrentUserId();
            final ForumUser creator = userService.findUser(creatorId);

            creator.addPost(newPost);

            final Post savedPost = postRepository.save(newPost);
            final PostDto postDto = postMapper.toDto(savedPost);
>>>>>>> tmp-1
            return new PostCreateResponse(postDto);
        } catch (RuntimeException e) {
            log.error("Cannot create post", e);
            throw new PostException("Cannot create post", e);
        }
    }

    @Override
    @Transactional
<<<<<<< HEAD
    @PreAuthorize("@postServiceSecurity.checkOwnership(#targetPostId) or hasRole('SUPERUSER')")
    public PostEditResponse editPost(Long targetPostId, PostEditRequest editRequest) {
        log.debug("Editing post: {}", editRequest);
        try {
            final Post post = postRepository.findActiveById(targetPostId).orElseThrow(EntityNotFoundException::new);
            post.setTitle(editRequest.getNewTitle());
            post.setText(editRequest.getText());
            final PostDto postDto = postMapper.toDto(post);
            return new PostEditResponse(postDto);
        } catch (EntityNotFoundException e) {
            log.error("Post not found when edit");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
=======
    @PreAuthorize("@postServiceSecurity.checkOwnership(#postEditData.postId) or hasRole('SUPERUSER')")
    public PostEditResponse editPost(PostEditData postEditData) {
        log.debug("Editing post: {}", postEditData);
        try {
            final Post post = postRepository.findById(postEditData.getPostId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));

            post.setTitle(postEditData.getNewTitle());
            post.setText(postEditData.getText());
            final PostDto postDto = postMapper.toDto(post);
            return new PostEditResponse(postDto);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (RuntimeException e) {
            log.error("Cannot edit post", e);
            throw new PostException("Cannot edit post", e);
>>>>>>> tmp-1
        }
    }

    @Override
    @Transactional
    @PreAuthorize("@postServiceSecurity.checkOwnership(#postId) or hasRole('SUPERUSER')")
    public PostDeleteResponse deletePost(Long postId) {
        log.debug("Deleting post with id={}", postId);
<<<<<<< HEAD
        try {
            final Post post = postRepository.findActiveById(postId).orElseThrow(EntityNotFoundException::new);
            post.disable();
            return PostDeleteResponse.deleted();
        } catch (EntityNotFoundException e) {
            log.error("Post not found when delete");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }
    }

    @Override
    public void addComment(Long targetPost, Comment comment) {
        log.debug("Adding new comment to post's comments collection: {}", comment);
        postRepository.findById(targetPost)
            .ifPresentOrElse(
                post -> post.addComment(comment),
                () -> { throw new RuntimeException("Post not found"); }
            );
=======

        final Post post = postRepository.findById(postId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));

        postRepository.delete(post);
        return PostDeleteResponse.deleted();
    }

    @Override
    public Post findPost(Long postId) {
        return postRepository.findById(postId)
            .orElseThrow(EntityNotFoundException::new);
>>>>>>> tmp-1
    }
}
