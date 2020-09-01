package com.topov.forum.service.comment;

import com.topov.forum.dto.CommentDto;
import com.topov.forum.dto.request.comment.CommentCreateRequest;
import com.topov.forum.dto.request.comment.CommentDeleteRequest;
import com.topov.forum.dto.response.comment.CommentCreateResponse;
import com.topov.forum.dto.response.comment.CommentDeleteResponse;
import com.topov.forum.dto.response.comment.CommentEditResponse;
import com.topov.forum.service.data.CommentCreateData;
import com.topov.forum.service.data.CommentEditData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

<<<<<<<HEAD
=======
    >>>>>>>tmp-1

public interface CommentService {
<<<<<<< HEAD
    CommentCreateResponse createComment(Long postId, CommentCreateRequest createRequest);
    CommentEditResponse editComment(CommentEditData editCommentRequest);
    CommentDeleteResponse deleteComment(Long commentId);
=======
    Page<CommentDto> getAllComments(Long postId, Pageable pageable);
    CommentCreateResponse createComment(CommentCreateData createCommentRequest);
    CommentEditResponse editComment(CommentEditData editCommentRequest);
    CommentDeleteResponse deleteComment(CommentDeleteRequest commentId);
>>>>>>> tmp-1
}
