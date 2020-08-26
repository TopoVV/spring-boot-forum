package com.topov.forum.controller;

import com.topov.forum.dto.PostDeleteResponse;
import com.topov.forum.dto.PostDto;
import com.topov.forum.dto.ShortPostDto;
import com.topov.forum.dto.request.post.PostCreateRequest;
import com.topov.forum.dto.request.post.PostEditRequest;
import com.topov.forum.dto.response.post.PostCreateResponse;
import com.topov.forum.dto.response.post.PostEditResponse;
import com.topov.forum.dto.response.OperationResponse;
import com.topov.forum.dto.response.ValidationError;
import com.topov.forum.service.data.PostEditData;
import com.topov.forum.service.post.PostService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@Log4j2
@Controller
public class PostController {
    private static final String POST_URI_TEMPLATE = "http://localhost:8080/posts/%d";
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @ResponseBody
    @GetMapping(value = "/posts/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long postId) {
        final PostDto post = postService.getPost(postId);
        postService.postViewed(postId);
        return ResponseEntity.ok(post);
    }

    @ResponseBody
    @GetMapping(value = "/posts")
    public ResponseEntity<Page<ShortPostDto>> getAllPosts(@PageableDefault(size = 3) Pageable pageable) {
        final Page<ShortPostDto> allPosts = postService.getAllPosts(pageable);
        return ResponseEntity.ok(allPosts);
    }

    @ResponseBody
    @PostMapping(
        value = "/posts",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<OperationResponse> createPost(@Valid @RequestBody PostCreateRequest postCreateRequest,
                                                        BindingResult bindingResult) {
        log.debug("Handling (POST) post creation request");
        if(bindingResult.hasErrors()) {
            final ValidationError validationError = new ValidationError(bindingResult);
            return ResponseEntity.badRequest().body(validationError);
        }

        final PostCreateResponse response = postService.createPost(postCreateRequest);
        final URI location = buildCreatedPostLocation(response);
        return ResponseEntity.created(location).body(response);
    }

    private URI buildCreatedPostLocation(PostCreateResponse postCreateResponse) {
        final String location = String.format(POST_URI_TEMPLATE, postCreateResponse.getPostDto().getPostId());
        return URI.create(location);
    }

    @ResponseBody
    @PutMapping(
        value = "/posts/{postId}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<OperationResponse> editPost(@PathVariable Long postId,
                                                      @Valid @RequestBody PostEditRequest postEditRequest,
                                                      BindingResult bindingResult) {
        log.debug("Handling (PUT) post modification request");
        if(bindingResult.hasErrors()) {
            final ValidationError validationError = new ValidationError(bindingResult);
            return ResponseEntity.badRequest().body(validationError);
        }
        final PostEditData postEditData = new PostEditData(postEditRequest, postId);
        final PostEditResponse response = postService.editPost(postEditData);
        return ResponseEntity.ok(response);
    }

    @ResponseBody
    @DeleteMapping(value = "/posts/{postId}")
    public ResponseEntity<OperationResponse> deletePost(@PathVariable Long postId) {
        log.debug("Handling (DELETE) post removal request");
        final PostDeleteResponse response = postService.deletePost(postId);
        return ResponseEntity.ok(response);
    }
}
