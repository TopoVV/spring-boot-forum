package com.topov.forum.service.comment;

import com.topov.forum.dto.request.CreateCommentRequest;
import com.topov.forum.dto.request.EditCommentRequest;
import com.topov.forum.dto.response.CreateCommentResponse;
import com.topov.forum.dto.response.EditCommentResponse;

public interface CommentService {
    CreateCommentResponse createComment(CreateCommentRequest createCommentRequest);
    EditCommentResponse editComment(EditCommentRequest editCommentRequest);
}
