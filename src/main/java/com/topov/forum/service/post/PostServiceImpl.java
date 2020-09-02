package com.topov.forum.service.post;

import com.topov.forum.dto.model.PostDto;
import com.topov.forum.dto.model.ShortPostDto;
import com.topov.forum.dto.request.post.PostCreateRequest;
import com.topov.forum.dto.response.post.PostCreateResponse;
import com.topov.forum.exception.PostException;
import com.topov.forum.mapper.PostMapper;
import com.topov.forum.model.ForumUser;
import com.topov.forum.model.Post;
import com.topov.forum.model.PostVisit;
import com.topov.forum.repository.PostRepository;
import com.topov.forum.security.AuthenticationService;
import com.topov.forum.service.VisitService;
import com.topov.forum.service.data.PostEditData;
import com.topov.forum.service.user.UserService;
import com.topov.forum.dto.ValidationErrors;
import com.topov.forum.validation.ValidationResult;
import com.topov.forum.validation.post.PostValidator;
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
import java.net.URI;


@Log4j2
@Service
public class PostServiceImpl implements PostService {
    private static final String POST_URI_TEMPLATE = "http://localhost:8080/posts/%d";

    private final AuthenticationService authenticatedUserService;
    private final PostValidator postValidator;
    private final PostRepository postRepository;
    private final VisitService visitService;
    private final UserService userService;
    private final PostMapper postMapper;

    @Autowired
    public PostServiceImpl(AuthenticationService authenticatedUserService,
                           PostRepository postRepository,
                           PostValidator postValidator,
                           VisitService visitService,
                           UserService userService,
                           PostMapper postMapper) {
        this.authenticatedUserService = authenticatedUserService;
        this.postValidator = postValidator;
        this.postRepository = postRepository;
        this.visitService = visitService;
        this.userService = userService;
        this.postMapper = postMapper;
    }

    @Override
    @Transactional
    public PostDto getPost(Long postId) {
        final Post post = postRepository.findById(postId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));

        final Long currentUserId = authenticatedUserService.getCurrentUserId();
        final PostDto postDto = postMapper.toDto(post);
        visitService.postVisited(new PostVisit(currentUserId, postId));
        return postDto;
    }

    @Override
    @Transactional
    public Page<ShortPostDto> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable)
            .map(postMapper::toShortDto);
    }

    @Override
    @Transactional
    public PostCreateResponse<?> createPost(PostCreateRequest postCreateRequest) {
        log.debug("Creating a post: {}", postCreateRequest);
        try {

            final ValidationResult validationResult = postValidator.validatePostCreationRequest(postCreateRequest);
            if (validationResult.isValid()) {
                final ValidationErrors validationErrors = new ValidationErrors(validationResult.getValidationErrors());
                return PostCreateResponse.builder()
                    .httpCode(HttpStatus.BAD_REQUEST)
                    .errors(validationErrors)
                    .message("Post cannot be created")
                    .build();
            }

            final Post newPost = new Post();
            newPost.setTitle(postCreateRequest.getTitle());
            newPost.setText(postCreateRequest.getText());

            final Long creatorId = authenticatedUserService.getCurrentUserId();
            final ForumUser creator = userService.findUser(creatorId);

            creator.addPost(newPost);

            final Post savedPost = postRepository.save(newPost);
            final PostDto postDto = postMapper.toDto(savedPost);
            final URI location = buildCreatedPostLocation(postDto.getPostId());
            return PostCreateResponse.builder()
                .httpCode(HttpStatus.CREATED)
                .data(postDto)
                .location(location)
                .message("A post has been created")
                .build();

        } catch (RuntimeException e) {
            log.error("Cannot create post", e);
            throw new PostException("Cannot create post", e);
        }
    }

    private URI buildCreatedPostLocation(Long postId) {
        final String location = String.format(POST_URI_TEMPLATE, postId);
        return URI.create(location);
    }

    @Override
    @Transactional
    @PreAuthorize("@postServiceSecurity.checkOwnership(#postEditData.postId) or hasRole('SUPERUSER')")
    public void editPost(PostEditData postEditData) {
        log.debug("Editing post: {}", postEditData);
        try {
            postValidator.validatePostEditRequest();

            final Post post = postRepository.findById(postEditData.getPostId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));

            post.setTitle(postEditData.getNewTitle());
            post.setText(postEditData.getText());
            final PostDto postDto = postMapper.toDto(post);
//            return new PostEditResult(postDto);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (RuntimeException e) {
            log.error("Cannot edit post", e);
            throw new PostException("Cannot edit post", e);
        }
    }

    @Override
    @Transactional
    @PreAuthorize("@postServiceSecurity.checkOwnership(#postId) or hasRole('SUPERUSER')")
    public void deletePost(Long postId) {
        log.debug("Deleting post with id={}", postId);

        final Post post = postRepository.findById(postId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));

        postRepository.delete(post);
//        return PostDeleteResult.deleted();
    }

    @Override
    public Post findPost(Long postId) {
        return postRepository.findById(postId)
            .orElseThrow(EntityNotFoundException::new);
    }
}
