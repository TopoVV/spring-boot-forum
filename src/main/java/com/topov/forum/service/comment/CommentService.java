package com.topov.forum.service.comment;

import com.topov.forum.dto.model.CommentDto;
import com.topov.forum.dto.request.comment.CommentDeleteRequest;
import com.topov.forum.service.data.CommentCreateData;
import com.topov.forum.service.data.CommentEditData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

public interface CommentService {
    @Transactional
    Page<CommentDto> getAllComments(Long postId, Pageable pageable);

    @Transactional
    void createComment(CommentCreateData commentCreateData);

    @Transactional
    @PreAuthorize("@commentServiceSecurity.checkOwnership(#commentEditData.targetCommentId) or hasRole('SUPERUSER')")
    void editComment(CommentEditData commentEditData);

    @Transactional
    @PreAuthorize("@commentServiceSecurity.checkOwnership(#commentDeleteRequest) or hasRole('SUPERUSER')")
    void deleteComment(CommentDeleteRequest commentDeleteRequest);
}
