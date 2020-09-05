package com.topov.forum.dto.response.post;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.topov.forum.dto.model.post.PostDto;
import com.topov.forum.dto.model.post.views.PostViews;
import com.topov.forum.dto.response.ApiResponse;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(value = "message")
public class PostGetResponse extends ApiResponse {
    private final PostDto postDto;
    public PostGetResponse(String status, PostDto postDto) {
        super(status);
        this.postDto = postDto;
    }
}
