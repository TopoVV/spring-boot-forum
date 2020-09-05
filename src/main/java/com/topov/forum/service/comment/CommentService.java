package com.topov.forum.service.comment;

import com.topov.forum.dto.model.CommentDto;
import com.topov.forum.dto.request.comment.CommentCreateRequest;
import com.topov.forum.dto.request.comment.CommentEditRequest;
import com.topov.forum.dto.result.OperationResult;
import com.topov.forum.dto.result.comment.CommentCreateResult;
import com.topov.forum.dto.result.comment.CommentDeleteResult;
import com.topov.forum.dto.result.comment.CommentEditResult;
import com.topov.forum.dto.result.comment.CommentGetAllResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

public interface CommentService {
    CommentGetAllResult getAllComments(Long postId, Pageable pageable);
    CommentCreateResult createComment(Long postId, CommentCreateRequest commentCreateRequest);
    CommentEditResult editComment(Long commentId, CommentEditRequest commentEditRequest);
    CommentDeleteResult deleteComment(Long commentId);
}
