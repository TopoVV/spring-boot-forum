package com.topov.forum.dto.model.post;

import com.fasterxml.jackson.annotation.JsonView;
import com.topov.forum.dto.model.post.views.PostViews;
import lombok.Data;

@Data
public class PostDto {
    @JsonView(PostViews.ShortPostView.class)
    private Long postId;
    @JsonView(PostViews.ShortPostView.class)
    private String title;
    @JsonView(PostViews.FullPostView.class)
    private String text;
    @JsonView(PostViews.ShortPostView.class)
    private String author;
    @JsonView(PostViews.ShortPostView.class)
    private Integer visitsAmount;
    @JsonView(PostViews.ShortPostView.class)
    private Integer commentsAmount;
}
