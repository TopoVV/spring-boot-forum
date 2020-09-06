package com.topov.forum.service.comment;

import com.topov.forum.dto.request.comment.CommentCreateRequest;
import com.topov.forum.dto.request.comment.CommentEditRequest;
import com.topov.forum.dto.result.comment.CommentCreateResult;
import com.topov.forum.dto.result.comment.CommentDeleteResult;
import com.topov.forum.dto.result.comment.CommentEditResult;
import com.topov.forum.dto.result.comment.CommentGetAllResult;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    CommentGetAllResult getAllComments(Long postId, Pageable pageable);
    CommentCreateResult createComment(Long postId, CommentCreateRequest commentCreateRequest);
    CommentEditResult editComment(Long commentId, CommentEditRequest commentEditRequest);
    CommentDeleteResult deleteComment(Long commentId);
}
