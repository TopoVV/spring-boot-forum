package com.topov.forum.service.comment;

import com.topov.forum.dto.result.OperationResult;
import com.topov.forum.dto.model.CommentDto;
import com.topov.forum.dto.request.comment.CommentDeleteRequest;
import com.topov.forum.service.data.CommentCreateData;
import com.topov.forum.service.data.CommentEditData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface CommentService {
    Page<CommentDto> getAllComments(Long postId, Pageable pageable);
    OperationResult createComment(CommentCreateData createCommentRequest);
    OperationResult editComment(CommentEditData editCommentRequest);
    OperationResult deleteComment(CommentDeleteRequest commentId);
}
