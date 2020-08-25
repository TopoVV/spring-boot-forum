package com.topov.forum.service;

import com.topov.forum.dto.request.CreateCommentRequest;
import com.topov.forum.dto.response.CreateCommentResponse;

public interface CommentService {
    CreateCommentResponse createComment(CreateCommentRequest createCommentRequest);
}
