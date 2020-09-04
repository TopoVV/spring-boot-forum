package com.topov.forum.service.comment;

import com.topov.forum.dto.model.CommentDto;
import com.topov.forum.dto.request.comment.CommentCreateRequest;
import com.topov.forum.dto.request.comment.CommentEditRequest;
import com.topov.forum.dto.result.OperationResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

public interface CommentService {
    @Transactional
    Page<CommentDto> getAllComments(Long postId, Pageable pageable);

    @Transactional
    OperationResult createComment(Long postId, CommentCreateRequest commentCreateRequest);

    @Transactional
    @PreAuthorize("@commentServiceSecurity.checkOwnership(#commentId) or hasRole('SUPERUSER')")
    OperationResult editComment(Long commentId, CommentEditRequest commentEditRequest);

    @Transactional
    @PreAuthorize("@commentServiceSecurity.checkOwnership(#commentDeleteRequest) or hasRole('SUPERUSER')")
    OperationResult deleteComment(Long commentId);
}
