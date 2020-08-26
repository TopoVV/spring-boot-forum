package com.topov.forum.dto.response.post;

import com.topov.forum.dto.response.OperationResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PostDeleteResponse extends OperationResponse {
    private static final String POST_SUCCESSFULLY_DELETED = "The post has been deleted";

    public static PostDeleteResponse deleted() {
        return new PostDeleteResponse(POST_SUCCESSFULLY_DELETED);
    }

    public PostDeleteResponse(String message) {
        super(message);
    }
}
