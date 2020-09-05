package com.topov.forum.dto.response.comment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.topov.forum.dto.model.CommentDto;
import com.topov.forum.dto.model.post.PostDto;
import com.topov.forum.dto.response.ApiResponse;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@JsonIgnoreProperties(value = "message")
public class CommentGetAllResponse extends ApiResponse {
    private Page<CommentDto> comments = Page.empty();
    public CommentGetAllResponse(String status, Page<CommentDto> comments) {
        super(status);
        this.comments = comments;
    }
}
