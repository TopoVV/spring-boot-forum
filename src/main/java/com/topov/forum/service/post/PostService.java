package com.topov.forum.service.post;

import com.topov.forum.dto.model.post.PostDto;

import com.topov.forum.dto.request.post.PostCreateRequest;
import com.topov.forum.dto.request.post.PostEditRequest;
import com.topov.forum.dto.result.OperationResult;
import com.topov.forum.dto.result.post.*;
import com.topov.forum.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PostService {
    PostGetResult getPost(Long postId);
    PostGetAllResult getAllPosts(Pageable pageable);
    PostCreateResult createPost(PostCreateRequest postCreateRequest);
    PostEditResult editPost(Long postId, PostEditRequest postEditRequest);
    PostDeleteResult deletePost(Long postId);

    Post findPost(Long postId);
}
