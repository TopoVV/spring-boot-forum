package com.topov.forum.service.comment;

import com.topov.forum.dto.request.comment.CommentCreateRequest;
import com.topov.forum.dto.response.comment.CommentDeleteResponse;
import com.topov.forum.service.data.CommentCreateData;
import com.topov.forum.service.data.CommentEditData;
import com.topov.forum.dto.response.comment.CommentCreateResponse;
import com.topov.forum.dto.response.comment.CommentEditResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

public interface CommentService {
    CommentCreateResponse createComment(Long postId, CommentCreateRequest createRequest);
    CommentEditResponse editComment(CommentEditData editCommentRequest);
    CommentDeleteResponse deleteComment(Long commentId);
}
