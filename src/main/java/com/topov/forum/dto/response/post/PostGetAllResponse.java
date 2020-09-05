package com.topov.forum.dto.response.post;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.topov.forum.dto.model.post.PostDto;
import com.topov.forum.dto.model.post.views.PostViews;
import com.topov.forum.dto.response.ApiResponse;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@JsonIgnoreProperties(value = "message")
public class PostGetAllResponse extends ApiResponse {
    private final Page<PostDto> posts;

    public PostGetAllResponse(String status, Page<PostDto> posts) {
        super(status);
        this.posts = posts;
    }
}
