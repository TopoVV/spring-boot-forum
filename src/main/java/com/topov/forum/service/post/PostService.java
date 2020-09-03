package com.topov.forum.service.post;

<<<<<<< HEAD
import com.topov.forum.dto.result.OperationResult;
=======
import com.topov.forum.dto.OperationResult;
>>>>>>> 282c7c2d0776712c32790097da1739769a0824f5
import com.topov.forum.dto.model.PostDto;
import com.topov.forum.dto.model.ShortPostDto;
import com.topov.forum.dto.request.post.PostCreateRequest;
import com.topov.forum.dto.request.post.PostEditRequest;
<<<<<<< HEAD
import com.topov.forum.dto.result.post.PostCreateResult;
import com.topov.forum.model.Post;
=======
import com.topov.forum.model.Post;
import com.topov.forum.service.data.PostEditData;
>>>>>>> 282c7c2d0776712c32790097da1739769a0824f5
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    PostDto getPost(Long postId);
    Page<ShortPostDto> getAllPosts(Pageable pageable);
    OperationResult createPost(PostCreateRequest postCreateRequest);
    OperationResult editPost(Long postId, PostEditRequest postEditRequest);
    OperationResult deletePost(Long postId);

    Post findPost(Long postId);
}
