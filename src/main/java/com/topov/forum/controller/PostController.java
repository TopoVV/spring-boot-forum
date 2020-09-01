package com.topov.forum.controller;

import com.topov.forum.dto.PostDto;
import com.topov.forum.dto.ShortPostDto;
import com.topov.forum.dto.request.post.PostCreateRequest;
import com.topov.forum.dto.request.post.PostEditRequest;
import com.topov.forum.dto.response.InvalidInputResponse;
import com.topov.forum.dto.response.OperationResponse;
import com.topov.forum.dto.response.post.PostCreateResponse;
import com.topov.forum.dto.response.post.PostDeleteResponse;
import com.topov.forum.dto.response.post.PostEditResponse;
import com.topov.forum.service.data.PostEditData;
import com.topov.forum.service.post.PostService;
import com.topov.forum.validation.PostValidationService;
import com.topov.forum.dto.response.ValidationErrorResponse;
import com.topov.forum.validation.ValidationResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@Log4j2
@RestController
public class PostController {
    private static final String POST_URI_TEMPLATE = "http://localhost:8080/posts/%d";
    private final PostService postService;
    private final PostValidationService postValidationService;


    @Autowired
    public PostController(PostService postService, PostValidationService postValidationService) {
        this.postService = postService;
        this.postValidationService = postValidationService;
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
    public ResponseEntity<OperationResponse> createPost(@Valid @RequestBody PostCreateRequest postCreateRequest) {
        log.debug("Handling (POST) post creation request");

        final ValidationResult validationResult = postValidationService.validateCreatePost(postCreateRequest);
        if (validationResult.hasErrors()) {
            final var validationErrorResponse = new ValidationErrorResponse(validationResult);
            return ResponseEntity.badRequest().body(validationErrorResponse);
        }

        final PostCreateResponse response = postService.createPost(postCreateRequest);
        final URI location = buildCreatedPostLocation(response);
        return ResponseEntity.created(location).body(response);
    }

    private URI buildCreatedPostLocation(PostCreateResponse postCreateResponse) {
        final String location = String.format(POST_URI_TEMPLATE, postCreateResponse.getPostDto().getPostId());
        return URI.create(location);
    }

    @PutMapping(
        value = "/posts/{postId}",
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<OperationResponse> editPost(@Valid @RequestBody PostEditRequest postEditRequest,
                                                      @PathVariable Long postId) {
        log.debug("Handling (PUT) post modification request");

        final ValidationResult validationResult = postValidationService.validateEditPost(postEditRequest);
        if(validationResult.hasErrors()) {
            final var validationErrorResponse = new ValidationErrorResponse(validationResult);
            return ResponseEntity.badRequest().body(validationErrorResponse);
        }

        final PostEditData postEditData = new PostEditData(postEditRequest, postId);
        final PostEditResponse response = postService.editPost(postEditData);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/posts/{postId}")
    public ResponseEntity<OperationResponse> deletePost(@PathVariable Long postId) {
        log.debug("Handling (DELETE) post removal request");
        final PostDeleteResponse response = postService.deletePost(postId);
        return ResponseEntity.ok(response);
    }
}
