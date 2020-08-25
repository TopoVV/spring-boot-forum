package com.topov.forum.controller;

import com.topov.forum.dto.request.CreateCommentRequest;
import com.topov.forum.dto.response.CreateCommentResponse;
import com.topov.forum.dto.response.OperationResponse;
import com.topov.forum.dto.response.ValidationError;
import com.topov.forum.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @ResponseBody
    @PostMapping(
        value = "/posts/{postId}/comments",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<OperationResponse> createComment(@Valid @RequestBody CreateCommentRequest createCommentRequest,
                                                           BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            final ValidationError validationError = new ValidationError(bindingResult);
            return ResponseEntity.badRequest().body(validationError);
        }

        final CreateCommentResponse response = commentService.createComment(createCommentRequest);
        return ResponseEntity.ok(response);
    }
}
