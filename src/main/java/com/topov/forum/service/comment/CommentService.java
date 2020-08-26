package com.topov.forum.service.comment;

import com.topov.forum.service.data.CommentCreateData;
import com.topov.forum.service.data.CommentEditData;
import com.topov.forum.dto.response.comment.CommentCreateResponse;
import com.topov.forum.dto.response.comment.CommentEditResponse;

public interface CommentService {
    CommentCreateResponse createComment(CommentCreateData createCommentRequest);
    CommentEditResponse editComment(CommentEditData editCommentRequest);
}
