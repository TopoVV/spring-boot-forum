package com.topov.forum.controller;

import com.topov.forum.dto.OperationResult;
import com.topov.forum.dto.model.PostDto;
import com.topov.forum.dto.model.ShortPostDto;
import com.topov.forum.dto.request.post.PostCreateRequest;
import com.topov.forum.dto.request.post.PostEditRequest;
import com.topov.forum.service.post.PostService;
import com.topov.forum.validation.post.PostValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    private final PostValidator postValidator;

    @Autowired
    public PostController(PostService postService, PostValidator postValidator) {
        this.postService = postService;
        this.postValidator = postValidator;
    }

    @GetMapping(value = "/posts/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long postId) {
        final PostDto post = postService.getPost(postId);
        return ResponseEntity.ok(post);
    }

    @GetMapping(value = "/posts")
    public ResponseEntity<Page<ShortPostDto>> getAllPosts(@PageableDefault(size = 3) Pageable pageable) {
        final Page<ShortPostDto> allPosts = postService.getAllPosts(pageable);
        return ResponseEntity.ok(allPosts);
    }

    @PostMapping(
        value = "/posts",
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<OperationResult> createPost(@Valid @RequestBody PostCreateRequest postCreateRequest) {
        log.debug("Handling (POST) post creation request");

        final OperationResult response = postService.createPost(postCreateRequest);
        return ResponseEntity.status(response.getHttpCode()).body(response);
    }

    @PutMapping(
        value = "/posts/{postId}",
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<OperationResult> editPost(@PathVariable Long postId, @Valid @RequestBody PostEditRequest postEditRequest) {
        log.debug("Handling (PUT) post modification request");

        final OperationResult response = postService.editPost(postId, postEditRequest);
        return ResponseEntity.status(response.getHttpCode()).body(response);
    }

    @DeleteMapping(value = "/posts/{postId}")
    public void deletePost(@PathVariable Long postId) {
        log.debug("Handling (DELETE) post removal request");
    }
}
