package com.topov.forum.validation.post.rule;

import com.topov.forum.dto.request.post.PostEditRequest;
import com.topov.forum.validation.ValidationRule;
import com.topov.forum.validation.post.constraint.PostExists;
import com.topov.forum.validation.post.constraint.TitleUnique;
import lombok.Getter;

import javax.validation.GroupSequence;

import static com.topov.forum.validation.post.rule.PostEditValidationRule.PostModificationChecks;

@Getter
@TitleUnique(groups = PostModificationChecks.class)
public class PostEditValidationRule extends ValidationRule {
    @PostExists(groups = PostPreModificationChecks.class)
    private final Long postId;
    private final String newTitle;
    private final String oldTitle;

    public PostEditValidationRule(Long postId, PostEditRequest editRequest) {
        this.postId = postId;
        this.newTitle = editRequest.getNewTitle();
        this.oldTitle = editRequest.getOldTitle();
    }

    @Override
    public Class<?> getValidationSequence() {
        return PostEditValidationSequence.class;
    }

    public interface PostPreModificationChecks {}
    public interface PostModificationChecks {}

    @GroupSequence({PostPreModificationChecks.class, PostModificationChecks.class})
    private interface PostEditValidationSequence {}
}
