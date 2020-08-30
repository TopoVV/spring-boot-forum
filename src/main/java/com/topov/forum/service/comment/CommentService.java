package com.topov.forum.service.comment;

import com.topov.forum.dto.response.comment.CommentCreateResponse;
import com.topov.forum.dto.response.comment.CommentDeleteResponse;
import com.topov.forum.dto.response.comment.CommentEditResponse;
import com.topov.forum.service.data.CommentCreateData;
import com.topov.forum.service.data.CommentEditData;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

public interface CommentService {
    CommentCreateResponse createComment(CommentCreateData createCommentRequest);
    CommentEditResponse editComment(CommentEditData editCommentRequest);

    @Transactional
    @PreAuthorize("@commentServiceSecurity.checkOwnership(#commentId) or hasRole('SUPERUSER')")
    CommentDeleteResponse deleteComment(Long commentId);
}
