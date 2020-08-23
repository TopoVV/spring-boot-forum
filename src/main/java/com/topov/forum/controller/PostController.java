package com.topov.forum.controller;

import com.topov.forum.dto.request.CreatePostRequest;
import com.topov.forum.dto.response.CreatePostResponse;
import com.topov.forum.dto.response.OperationResponse;
import com.topov.forum.dto.response.ValidationError;
import com.topov.forum.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@Controller
public class PostController {
    private static final String POST_URI_TEMPLATE = "http://localhost:8080/posts/%d";
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping(value = "/posts")
    public String createPostPage() {
        return "post-create";
    }

    @ResponseBody
    @PostMapping(
        value = "/posts",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<OperationResponse> createPost(@Valid @RequestBody CreatePostRequest createPostRequest,
                                                        BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            final ValidationError validationError = new ValidationError(bindingResult);
            return ResponseEntity.badRequest().body(validationError);
        }

        final CreatePostResponse response = postService.createPost(createPostRequest);
        final URI uri = assembleCreatedResourceLocation(response);
        return ResponseEntity.created(uri).body(response);
    }

    private URI assembleCreatedResourceLocation(CreatePostResponse createPostResponse) {
        final String location = String.format(POST_URI_TEMPLATE, createPostResponse.getPostDto().getPostId());
        return URI.create(location);
    }
}
