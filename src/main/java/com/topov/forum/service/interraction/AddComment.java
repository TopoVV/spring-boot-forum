package com.topov.forum.service.interraction;

import com.topov.forum.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddComment {
    private final Long targetId;
    private final Comment newComment;
}
