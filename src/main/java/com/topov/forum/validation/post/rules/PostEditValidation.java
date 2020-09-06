package com.topov.forum.validation.post.rules;

import com.topov.forum.dto.request.post.PostEditRequest;
import com.topov.forum.validation.ValidationRule;
import com.topov.forum.validation.comment.constraint.PostExists;
import com.topov.forum.validation.post.constraint.UniqueTitle;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@UniqueTitle
public class PostEditValidation extends ValidationRule {
    @PostExists
    private final Long postId;
    private final String newTitle;
    private final String oldTitle;

    public PostEditValidation(Long postId, PostEditRequest editRequest) {
        this.postId = postId;
        this.newTitle = editRequest.getNewTitle();
        this.oldTitle = editRequest.getOldTitle();
    }
}
