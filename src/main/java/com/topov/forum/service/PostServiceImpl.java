package com.topov.forum.service;

import com.topov.forum.dto.request.CreatePostRequest;
import com.topov.forum.dto.response.CreatePostResponse;
import com.topov.forum.exception.PostException;
import com.topov.forum.model.Post;
import com.topov.forum.repository.PostRepository;
import com.topov.forum.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthenticatedUserService authenticatedUserService;
    @Autowired
    public PostServiceImpl(PostRepository postRepository,
                           UserRepository repository,
                           AuthenticatedUserService authenticatedUserService) {
        this.authenticatedUserService = authenticatedUserService;
        this.postRepository = postRepository;
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
            return new CreatePostResponse("The post has been created", newPost.getPostId());
        } catch (RuntimeException e) {
            log.error("Cannot create post", e);
            throw new PostException("Cannot create post");
        }
    }

    private Post assemblePost(CreatePostRequest createPostRequest) {
        final Post newPost = new Post();
        newPost.setTitle(createPostRequest.getTitle());
        newPost.setText(createPostRequest.getText());
        return newPost;
    }
}
