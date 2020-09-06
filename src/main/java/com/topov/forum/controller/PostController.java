package com.topov.forum.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.topov.forum.dto.model.post.views.PostViews;
import com.topov.forum.dto.request.post.PostCreateRequest;
import com.topov.forum.dto.request.post.PostEditRequest;
import com.topov.forum.dto.response.ApiResponse;
import com.topov.forum.dto.result.post.*;
import com.topov.forum.service.post.PostService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Log4j2
@RestController
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @JsonView(PostViews.FullPostView.class)
    @GetMapping(value = "/posts/{postId}")
    public ResponseEntity<ApiResponse> getPost(@PathVariable Long postId) {
        final PostGetResult result = postService.getPost(postId);
        return result.createResponseEntity();
    }

    @JsonView(PostViews.ShortPostView.class)
    @GetMapping(value = "/posts")
    public ResponseEntity<ApiResponse> getAllPosts(@PageableDefault(size = 3) Pageable pageable) {
        final PostGetAllResult result = postService.getAllPosts(pageable);
        return result.createResponseEntity();
    }

    @JsonView(value = PostViews.ShortPostView.class)
    @PostMapping(
        value = "/posts",
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ApiResponse> createPost(@Valid @RequestBody PostCreateRequest postCreateRequest) {
        log.debug("Handling (POST) post creation request");
        final PostCreateResult operationResult = postService.createPost(postCreateRequest);
        return operationResult.createResponseEntity();
    }

    @JsonView(value = PostViews.FullPostView.class)
    @PutMapping(
        value = "/posts/{postId}",
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ApiResponse> editPost(@PathVariable Long postId, @Valid @RequestBody PostEditRequest postEditRequest) {
        log.debug("Handling (PUT) post modification request");
        final PostEditResult operationResult = postService.editPost(postId, postEditRequest);
        return operationResult.createResponseEntity();
    }

    @DeleteMapping(value = "/posts/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Long postId) {
        log.debug("Handling (DELETE) post removal request");
        final PostDeleteResult operationResult = postService.deletePost(postId);
        return operationResult.createResponseEntity();
    }
}
