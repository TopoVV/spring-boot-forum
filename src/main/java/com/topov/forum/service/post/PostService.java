package com.topov.forum.service.post;
import com.topov.forum.dto.PostDto;
import com.topov.forum.dto.ShortPostDto;
import com.topov.forum.dto.request.post.PostCreateRequest;
import com.topov.forum.dto.response.post.PostCreateResponse;
import com.topov.forum.dto.response.post.PostDeleteResponse;
import com.topov.forum.dto.response.post.PostEditResponse;
import com.topov.forum.model.Post;
import com.topov.forum.service.data.PostEditData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    PostCreateResponse createPost(PostCreateRequest postCreateRequest);
    PostEditResponse editPost(PostEditData postEditData);
    Page<ShortPostDto> getAllPosts(Pageable pageable);
    PostDto getPost(Long postId);
    PostDeleteResponse deletePost(Long postId);

    Post findPost(Long postId);
}
