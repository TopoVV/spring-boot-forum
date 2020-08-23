package com.topov.forum.controller;

import com.topov.forum.dto.request.CreatePostRequest;
import com.topov.forum.dto.response.CreatePostResponse;
import com.topov.forum.dto.response.OperationResponse;
import com.topov.forum.dto.response.ValidationError;
import com.topov.forum.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.awt.*;
import java.net.URI;

@RestController
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping(
        value = "/posts",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<OperationResponse> createPost(@Valid @RequestBody CreatePostRequest createPostRequest,
                                                        BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            final ValidationError response = new ValidationError(bindingResult);
            return ResponseEntity.badRequest().body(response);
        }

        final CreatePostResponse response = postService.createPost(createPostRequest);
        final URI uri = URI.create(String.format("http://localhost:8080/posts/%d", response.getCreatedPostId()));
        return ResponseEntity.created(uri).body(response);
    }
}
