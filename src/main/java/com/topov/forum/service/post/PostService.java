package com.topov.forum.service.post;

import com.topov.forum.dto.PostDto;
import com.topov.forum.dto.ShortPostDto;
import com.topov.forum.dto.request.CreatePostRequest;
import com.topov.forum.dto.request.EditPostRequest;
import com.topov.forum.dto.response.CreatePostResponse;
import com.topov.forum.dto.response.EditPostResponse;
import com.topov.forum.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    CreatePostResponse createPost(CreatePostRequest createPostRequest);
    EditPostResponse editPost(EditPostRequest editPostRequest);
    Page<ShortPostDto> getAllPosts(Pageable pageable);
    PostDto getPost(Long postId);
    void deletePost(Long postId);
    void postViewed(Long postId);
}
