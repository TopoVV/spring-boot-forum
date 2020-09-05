package com.topov.forum.service.post;

import com.topov.forum.dto.model.post.PostDto;

import com.topov.forum.dto.request.post.PostCreateRequest;
import com.topov.forum.dto.request.post.PostEditRequest;
import com.topov.forum.dto.result.OperationResult;
import com.topov.forum.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PostService {
    PostDto getPost(Long postId);
    Page<PostDto> getAllPosts(Pageable pageable);
    OperationResult createPost(PostCreateRequest postCreateRequest);
    OperationResult editPost(Long postId, PostEditRequest postEditRequest);
    OperationResult deletePost(Long postId);

    Post findPost(Long postId);
}
