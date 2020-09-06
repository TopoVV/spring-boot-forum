package com.topov.forum.controller;

import com.topov.forum.dto.request.comment.CommentCreateRequest;
import com.topov.forum.dto.request.comment.CommentEditRequest;
import com.topov.forum.dto.response.ApiResponse;
import com.topov.forum.dto.result.OperationResult;
import com.topov.forum.dto.result.comment.CommentGetAllResult;
import com.topov.forum.service.comment.CommentService;
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
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping(value = "/posts/{postId}/comments")
    public ResponseEntity<ApiResponse> getCommentsForPost(@PageableDefault(size = 3) Pageable pageable,
                                                          @PathVariable Long postId) {
        log.debug("Handling (GET) comments");
        CommentGetAllResult result =  commentService.getAllComments(postId, pageable);
        return result.createResponseEntity();
    }

    @PostMapping(
        value = "/posts/{postId}/comments",
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ApiResponse> createComment(@Valid @RequestBody CommentCreateRequest commentCreateRequest,
                                                     @PathVariable Long postId) {
        log.debug("Handling (POST) comment creation request");
        final OperationResult result = commentService.createComment(postId, commentCreateRequest);
        return result.createResponseEntity();
    }

    @PutMapping(
        value = "/posts/{postId}/comments/{commentId}",
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ApiResponse> editComment(@Valid @RequestBody CommentEditRequest commentEditRequest,
                                                   @PathVariable Long commentId) {
        log.debug("Handling (PUT) comment edition request");
        final OperationResult result = commentService.editComment(commentId, commentEditRequest);
        return result.createResponseEntity();
    }

    @DeleteMapping(value = "/posts/{postId}/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Long commentId) {
        final OperationResult result = commentService.deleteComment(commentId);
        return result.createResponseEntity();
    }
}
