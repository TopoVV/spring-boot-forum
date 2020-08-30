package com.topov.forum.service.post;

import com.topov.forum.dto.PostDto;
import com.topov.forum.dto.ShortPostDto;
import com.topov.forum.dto.request.post.PostCreateRequest;
import com.topov.forum.dto.response.post.PostCreateResponse;
import com.topov.forum.dto.response.post.PostDeleteResponse;
import com.topov.forum.dto.response.post.PostEditResponse;
import com.topov.forum.exception.PostException;
import com.topov.forum.mapper.PostMapper;
import com.topov.forum.model.ForumUser;
import com.topov.forum.model.Post;
import com.topov.forum.model.PostVisit;
import com.topov.forum.model.Status;
import com.topov.forum.repository.PostRepository;
import com.topov.forum.repository.UserRepository;
import com.topov.forum.security.AuthenticationService;
import com.topov.forum.service.VisitService;
import com.topov.forum.service.data.PostEditData;
import com.topov.forum.service.user.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Log4j2
@Service
public class PostServiceImpl implements PostService {
    private final AuthenticationService authenticatedUserService;
    private final PostRepository postRepository;
    private final UserService userService;
    private final VisitService visitService;
    private final PostMapper postMapper;

    @Autowired
    public PostServiceImpl(AuthenticationService authenticatedUserService,
                           PostRepository postRepository,
                           VisitService visitService,
                           UserService userService,
                           PostMapper postMapper) {
        this.authenticatedUserService = authenticatedUserService;
        this.postRepository = postRepository;
        this.visitService = visitService;
        this.userService = userService;
        this.postMapper = postMapper;
    }

    @Override
    @Transactional
    public PostDto getPost(Long postId) {
        try {
            final Optional<Post> optionalPost = postRepository.findById(postId);
            if(optionalPost.isPresent()) {
                final Post post = optionalPost.get();
                final Long currentUserId = authenticatedUserService.getCurrentUserId();
                final PostDto postDto = postMapper.toDto(post);
                visitService.postVisited(new PostVisit(currentUserId, postId));
                return postDto;
            } else {
                throw new EntityNotFoundException();
            }
        } catch (EntityNotFoundException e) {
            log.debug("Post not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }
    }

    @Override
    @Transactional
    public Page<ShortPostDto> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable)
            .map(postMapper::toShortDto);
    }

    @Override
    @Transactional
    public PostCreateResponse createPost(PostCreateRequest postCreateRequest) {
        log.debug("Creating a post: {}", postCreateRequest);
        try {
            final Post newPost = assemblePost(postCreateRequest);
            final Long creatorId = authenticatedUserService.getCurrentUserId();
            final ForumUser creator = userService.getUser(creatorId);
            creator.addPost(newPost);
            final Post savedPost = postRepository.save(newPost);
            final PostDto postDto = postMapper.toDto(savedPost);
            return new PostCreateResponse(postDto);
        } catch (RuntimeException e) {
            log.error("Cannot create post", e);
            throw new PostException("Cannot create post", e);
        }
    }

    private Post assemblePost(PostCreateRequest postCreateRequest) {
        final Post newPost = new Post();
        newPost.setTitle(postCreateRequest.getTitle());
        newPost.setText(postCreateRequest.getText());
        newPost.setStatus(Status.ACTIVE);
        return newPost;
    }

    @Override
    @Transactional
    @PreAuthorize("@postServiceSecurity.checkOwnership(#postEditData.postId) or hasRole('SUPERUSER')")
    public PostEditResponse editPost(PostEditData postEditData) {
        log.debug("Editing post: {}", postEditData);
        return postRepository.findById(postEditData.getPostId())
            .map(post -> doEditPost(postEditData, post))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND , "Post not found"));
    }

    private PostEditResponse doEditPost(PostEditData editData, Post post) {
        if (post.isActive()) {
            post.setTitle(editData.getNewTitle());
            post.setText(editData.getText());
            final PostDto postDto = postMapper.toDto(post);
            return new PostEditResponse(postDto);
        }
        return PostEditResponse.postDisabled();
    }

    @Transactional
    @PreAuthorize("@postServiceSecurity.checkOwnership(#postId) or hasRole('SUPERUSER')")
    public PostDeleteResponse deletePost(Long postId) {
        log.debug("Deleting post with id={}", postId);
        return postRepository.findById(postId)
            .map(this::doDelete)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));
    }

    private PostDeleteResponse doDelete(Post post) {
        if(post.isActive()) {
            post.disable();
        }
        return PostDeleteResponse.deleted();
    }

}
