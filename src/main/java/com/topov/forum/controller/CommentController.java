package com.topov.forum.controller;

import com.topov.forum.dto.CommentDto;
import com.topov.forum.dto.request.comment.CommentCreateRequest;
import com.topov.forum.dto.request.comment.CommentEditRequest;
import com.topov.forum.dto.response.InputErrorResponse;
import com.topov.forum.dto.response.OperationResponse;
import com.topov.forum.dto.response.comment.CommentCreateResponse;
import com.topov.forum.dto.response.comment.CommentDeleteResponse;
import com.topov.forum.dto.response.comment.CommentEditResponse;
import com.topov.forum.service.comment.CommentService;
import com.topov.forum.service.data.CommentCreateData;
import com.topov.forum.service.data.CommentEditData;
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

@Log4j2
@RestController
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping(value = "/posts/{postId}/comments")
    public ResponseEntity<Page<CommentDto>> getCommentsForPost(@PathVariable Long postId,
                                                               @PageableDefault(size = 3) Pageable pageable) {
        Page<CommentDto> comments =  commentService.getAllComments(postId, pageable);
        return ResponseEntity.ok(comments);
    }

    @PostMapping(
        value = "/posts/{postId}/comments",
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<OperationResponse> createComment(@PathVariable Long postId,
                                                           @Valid @RequestBody CommentCreateRequest commentCreateRequest,
                                                           BindingResult bindingResult) {
        log.debug("Handling (POST) comment creation request");
        if(bindingResult.hasErrors()) {
            final InputErrorResponse inputErrorResponse = new InputErrorResponse(bindingResult);
            return ResponseEntity.badRequest().body(inputErrorResponse);
        }

        final CommentCreateData commentCreateData = new CommentCreateData(commentCreateRequest, postId);
        final CommentCreateResponse response = commentService.createComment(commentCreateData);
        return ResponseEntity.ok(response);
    }

    @PutMapping(
        value = "/posts/{postId}/comments/{commentId}",
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<OperationResponse> editComment(@PathVariable Long postId,
                                                         @PathVariable Long commentId,
                                                         @Valid @RequestBody CommentEditRequest commentEditRequest,
                                                         BindingResult bindingResult) {
        log.debug("Handling (PUT) comment edition request");
        if(bindingResult.hasErrors()) {
            final InputErrorResponse inputErrorResponse = new InputErrorResponse(bindingResult);
            return ResponseEntity.badRequest().body(inputErrorResponse);
        }

        final CommentEditData commentEditData = new CommentEditData(commentEditRequest, postId, commentId);
        final CommentEditResponse response = commentService.editComment(commentEditData);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/posts/{postId}/comments/{commentId}")
    public ResponseEntity<OperationResponse> deleteComment(@PathVariable Long commentId) {
        final CommentDeleteResponse commentDeleteResponse = commentService.deleteComment(commentId);
        return ResponseEntity.ok(commentDeleteResponse);
    }
}
