package com.topov.forum.dto.response.post;

import com.fasterxml.jackson.annotation.JsonView;
import com.topov.forum.dto.model.post.PostDto;
import com.topov.forum.dto.model.post.views.PostViews;
import com.topov.forum.dto.response.ApiResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
public class PostCreateResponse extends ApiResponse {
    @JsonView(PostViews.ShortPostView.class)
    private final PostDto postDto;

    public PostCreateResponse(String message, String status, PostDto postDto) {
        super(message, status);
        this.postDto = postDto;
    }
}
