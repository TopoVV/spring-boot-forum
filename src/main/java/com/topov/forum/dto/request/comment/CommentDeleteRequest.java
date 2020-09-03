package com.topov.forum.dto.request.comment;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class CommentDeleteRequest {
    @NotNull
    private Long targetCommentId;
}
