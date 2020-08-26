package com.topov.forum.service.post;

import com.topov.forum.service.data.PostEditData;
import com.topov.forum.dto.PostDto;
import com.topov.forum.dto.ShortPostDto;
import com.topov.forum.dto.request.post.PostCreateRequest;
import com.topov.forum.dto.response.post.PostCreateResponse;
import com.topov.forum.dto.response.post.PostEditResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    PostCreateResponse createPost(PostCreateRequest postCreateRequest);
    PostEditResponse editPost(PostEditData editPostRequest);
    Page<ShortPostDto> getAllPosts(Pageable pageable);
    PostDto getPost(Long postId);
    void deletePost(Long postId);
    void postViewed(Long postId);
}
