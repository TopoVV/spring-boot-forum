package com.topov.forum.service.post;

import com.topov.forum.dto.response.post.PostDeleteResponse;
import com.topov.forum.model.ViewCounter;
import com.topov.forum.service.ViewCounterService;
import com.topov.forum.service.data.PostEditData;
import com.topov.forum.dto.PostDto;
import com.topov.forum.dto.ShortPostDto;
import com.topov.forum.dto.request.post.PostCreateRequest;
import com.topov.forum.dto.response.post.PostCreateResponse;
import com.topov.forum.dto.response.post.PostEditResponse;
import com.topov.forum.exception.PostException;
import com.topov.forum.mapper.PostMapper;
import com.topov.forum.model.Post;
import com.topov.forum.model.Status;
import com.topov.forum.repository.PostRepository;
import com.topov.forum.security.AuthenticationService;
import com.topov.forum.service.interraction.AddComment;
import com.topov.forum.service.interraction.AddPost;
import com.topov.forum.service.user.UserServiceInternal;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Log4j2
@Service
public class PostServiceImpl implements PostService, PostServiceInternal {
    private final PostMapper postMapper;
    private final PostRepository postRepository;
    private final UserServiceInternal userService;
    private final AuthenticationService authenticatedUserService;
    private final ViewCounterService viewCounterService;

    @Autowired
    public PostServiceImpl(PostRepository postRepository,
                           PostMapper postMapper,
                           UserServiceInternal userService,
                           AuthenticationService authenticatedUserService,
                           ViewCounterService viewCounterService) {
        this.authenticatedUserService = authenticatedUserService;
        this.postRepository = postRepository;
        this.userService = userService;
        this.postMapper = postMapper;
        this.viewCounterService = viewCounterService;
    }

    @Override
    @Transactional
    public Page<ShortPostDto> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable)
            .map(postMapper::toShortDto);
    }

    @Override
    @Transactional
    public PostDto getPost(Long postId) {
        try {
            return postRepository.findById(postId)
                .map(postMapper::toDto)
                .orElseThrow(EntityNotFoundException::new);
        } catch (EntityNotFoundException e) {
            log.debug("Post not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }
    }

    @Override
    public void postViewed(Long postId) {
        viewCounterService.postViewed(postId);
    }

    @Override
    @Transactional
    public PostCreateResponse createPost(PostCreateRequest postCreateRequest) {
        log.debug("Creating a post: {}", postCreateRequest);
        try {
            final Post newPost = assemblePost(postCreateRequest);
            final Long currentUserId = authenticatedUserService.getCurrentUserId();

            userService.addPost(new AddPost(currentUserId, newPost));
            final PostDto postDto = postMapper.toDto(newPost);
            return new PostCreateResponse(postDto);
        } catch (RuntimeException e) {
            log.error("Cannot create post", e);
            throw new PostException("Cannot create post", e);
        }
    }

    private Post assemblePost(PostCreateRequest postCreateRequest) {
        final Post newPost = new Post();
        newPost.setTitle(postCreateRequest.getTitle());
        newPost.setText(postCreateRequest.getText());
        newPost.setStatus(Status.ACTIVE);
        final ViewCounter viewCounter = new ViewCounter();
        viewCounter.setPost(newPost);
        newPost.setViews(viewCounter);
        return newPost;
    }

    @Override
    @Transactional
    @PreAuthorize("@postServiceSecurity.checkOwnership(#postEditData.postId) or hasRole('SUPERUSER')")
    public PostEditResponse editPost(PostEditData postEditData) {
        log.debug("Editing post: {}", postEditData);
        return postRepository.findById(postEditData.getPostId())
            .map(post -> doEditPost(postEditData, post))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND , "Post not found"));
    }

    private PostEditResponse doEditPost(PostEditData editData, Post post) {
        if (post.isActive()) {
            post.setTitle(editData.getNewTitle());
            post.setText(editData.getText());
            final PostDto postDto = postMapper.toDto(post);
            return new PostEditResponse(postDto);
        }
        return PostEditResponse.postDisabled();
    }

    @Transactional
    @PreAuthorize("@postServiceSecurity.checkOwnership(#postId) or hasRole('SUPERUSER')")
    public PostDeleteResponse deletePost(Long postId) {
        log.debug("Deleting post with id={}", postId);
        return postRepository.findById(postId)
            .map(this::doDelete)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));
    }

    private PostDeleteResponse doDelete(Post post) {
        if(post.isActive()) {
            post.disable();
        }
        return PostDeleteResponse.deleted();
    }

    @Override
    public void addComment(AddComment addComment) {
        log.debug("Adding new comment to post's comments collection: {}", addComment);
        postRepository.findById(addComment.getTargetId())
            .ifPresentOrElse(
                post -> post.addComment(addComment.getNewComment()),
                () -> { throw new RuntimeException("Post not found"); }
            );
    }

}
