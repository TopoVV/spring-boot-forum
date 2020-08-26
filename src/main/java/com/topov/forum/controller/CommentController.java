package com.topov.forum.controller;

import com.topov.forum.dto.request.CreateCommentRequest;
import com.topov.forum.dto.request.EditCommentRequest;
import com.topov.forum.dto.response.CreateCommentResponse;
import com.topov.forum.dto.response.EditCommentResponse;
import com.topov.forum.dto.response.OperationResponse;
import com.topov.forum.dto.response.ValidationError;
import com.topov.forum.service.comment.CommentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Log4j2
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
        log.debug("Handling (POST) comment creation request");
        if(bindingResult.hasErrors()) {
            final ValidationError validationError = new ValidationError(bindingResult);
            return ResponseEntity.badRequest().body(validationError);
        }

        final CreateCommentResponse response = commentService.createComment(createCommentRequest);
        return ResponseEntity.ok(response);
    }

    @ResponseBody
    @PutMapping(
        value = "/posts/{postId}/comments/{commentId}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<OperationResponse> editComment(@Valid @RequestBody EditCommentRequest editCommentRequest,
                                                         BindingResult bindingResult) {
        log.debug("Handling (PUT) comment edition request");
        if(bindingResult.hasErrors()) {
            final ValidationError validationError = new ValidationError(bindingResult);
            return ResponseEntity.badRequest().body(validationError);
        }

        final EditCommentResponse response = commentService.editComment(editCommentRequest);
        return ResponseEntity.ok(response);
    }
}
