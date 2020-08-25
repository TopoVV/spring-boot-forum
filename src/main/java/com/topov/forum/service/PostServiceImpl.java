package com.topov.forum.service;

import com.topov.forum.dto.PostDto;
import com.topov.forum.dto.request.CreatePostRequest;
import com.topov.forum.dto.request.EditPostRequest;
import com.topov.forum.dto.response.CreatePostResponse;
import com.topov.forum.dto.response.EditPostResponse;
import com.topov.forum.exception.PostException;
import com.topov.forum.mapper.PostMapper;
import com.topov.forum.model.Post;
import com.topov.forum.repository.PostRepository;
import com.topov.forum.repository.UserRepository;
import com.topov.forum.security.AuthenticationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Log4j2
@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserRepository userRepository;
    private final AuthenticationService authenticatedUserService;

    @Autowired
    public PostServiceImpl(PostRepository postRepository,
                           PostMapper postMapper, UserRepository repository,
                           AuthenticationService authenticatedUserService) {
        this.authenticatedUserService = authenticatedUserService;
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        userRepository = repository;
    }

    @Override
    @Transactional
    public CreatePostResponse createPost(CreatePostRequest createPostRequest) {
        log.debug("Creating a post: {}", createPostRequest);
        try {
            final Post newPost = assemblePost(createPostRequest);
            final String creatorUsername = authenticatedUserService.getAuthenticatedUser().getUsername();
            userRepository.findByUsername(creatorUsername)
                .orElseThrow(() -> new RuntimeException("Cannot create post! User not found"))
                .addPost(newPost);
            postRepository.flush();
            final PostDto postDto = postMapper.toDto(newPost);
            return new CreatePostResponse(postDto);
        } catch (RuntimeException e) {
            log.error("Cannot create post", e);
            throw new PostException("Cannot create post");
        }
    }

    @Override
    @Transactional
    @PreAuthorize("@postServiceSecurity.checkOwnership(#editPostRequest.postId) or hasRole('SUPERUSER')")
    public EditPostResponse editPost(EditPostRequest editPostRequest) {
        log.debug("Editing post: {}", editPostRequest);
        return postRepository.findById(editPostRequest.getPostId())
            .map(post -> {
                post.setText(editPostRequest.getText());
                post.setTitle(editPostRequest.getNewTitle());
                postRepository.flush();
                return post;
            })
            .map(postMapper::toDto)
            .map(EditPostResponse::new)
            .orElseThrow(() -> {
                log.error("Post with id={} doesnt exist", editPostRequest.getPostId());
                return new ResponseStatusException(HttpStatus.NOT_FOUND , "Post not found");
            });
    }

    @Transactional
    @PreAuthorize("@postServiceSecurity.checkOwnership(#postId) or hasRole('SUPERUSER')")
    public void deletePost(Long postId) {
        log.debug("Deleting post id={}", postId);
        postRepository.findById(postId)
            .orElseThrow(() -> {
                log.error("Post not found id={}", postId);
                return new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
            })
            .disable();
    }

    private Post assemblePost(CreatePostRequest createPostRequest) {
        final Post newPost = new Post();
        newPost.setTitle(createPostRequest.getTitle());
        newPost.setText(createPostRequest.getText());
        newPost.setStatus(Post.Status.ACTIVE);
        return newPost;
    }
}
