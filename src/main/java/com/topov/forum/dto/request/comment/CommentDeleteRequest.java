package com.topov.forum.dto.request.comment;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentDeleteRequest {
    private Long targetCommentId;
}
