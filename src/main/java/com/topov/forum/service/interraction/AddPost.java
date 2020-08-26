package com.topov.forum.service.interraction;

import com.topov.forum.model.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddPost {
    private final Long targetId;
    private final Post newPost;
}
