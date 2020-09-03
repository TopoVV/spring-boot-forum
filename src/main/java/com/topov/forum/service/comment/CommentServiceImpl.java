package com.topov.forum.service.comment;

<<<<<<< HEAD
import com.topov.forum.dto.result.OperationResult;
=======
import com.topov.forum.dto.OperationResult;
import com.topov.forum.dto.OperationResultFail;
import com.topov.forum.dto.OperationResultSuccess;
>>>>>>> 282c7c2d0776712c32790097da1739769a0824f5
import com.topov.forum.dto.model.CommentDto;
import com.topov.forum.dto.request.comment.CommentDeleteRequest;
import com.topov.forum.exception.CommentException;
import com.topov.forum.mapper.CommentMapper;
import com.topov.forum.model.Comment;
import com.topov.forum.model.ForumUser;
import com.topov.forum.model.Post;
import com.topov.forum.repository.CommentRepository;
import com.topov.forum.security.AuthenticationService;
import com.topov.forum.service.data.CommentCreateData;
import com.topov.forum.service.data.CommentEditData;
import com.topov.forum.service.post.PostService;
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


@Log4j2
@Service
public class CommentServiceImpl implements CommentService {
    private final AuthenticationService authenticationService;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    private final UserService userService;
    private final PostService postService;


    @Autowired
    public CommentServiceImpl(AuthenticationService authenticationService,
                              CommentRepository commentRepository,
                              CommentMapper commentMapper,
                              UserService userService,
                              PostService postService) {
        this.authenticationService = authenticationService;
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.userService = userService;
        this.postService = postService;
    }

    @Override
    @Transactional
    public Page<CommentDto> getAllComments(Long postId, Pageable pageable) {
        return commentRepository.findCommentsForPost(postId, pageable)
            .map(commentMapper::toDto);
    }

    @Override
    @Transactional
    public OperationResult createComment(CommentCreateData commentCreateData) {
        log.debug("Creating comment: {}", commentCreateData);
        try {
            final Comment newComment = new Comment();
            newComment.setText(commentCreateData.getText());

            final Long targetPostId = commentCreateData.getTargetPostId();
            final Long creatorId = authenticationService.getCurrentUserId();

            final ForumUser creator = userService.findUser(creatorId);
            final Post post = postService.findPost(targetPostId);

            creator.addComment(newComment);
            post.addComment(newComment);

            final Comment savedComment = commentRepository.save(newComment);
            final CommentDto commentDto = commentMapper.toDto(savedComment);
<<<<<<< HEAD
            return null;
//            return ResponseSuccess.builder()
//                .httpCode(HttpStatus.CREATED)
//                .message("Comment created")
//                .data(commentDto)
//                .build();
=======
            return OperationResultSuccess.builder()
                .httpCode(HttpStatus.CREATED)
                .message("Comment created")
                .data(commentDto)
                .build();
>>>>>>> 282c7c2d0776712c32790097da1739769a0824f5
        } catch (RuntimeException e) {
            log.error("Error creating comment", e);
            throw new CommentException("Cannot create comment", e);
        }
    }

    @Override
    @Transactional
    @PreAuthorize("@commentServiceSecurity.checkOwnership(#commentEditData.targetCommentId) or hasRole('SUPERUSER')")
    public OperationResult editComment(CommentEditData commentEditData) {
        log.debug("Editing comment: {}", commentEditData);
        try {
            final Long targetCommentId = commentEditData.getTargetCommentId();

            final Comment comment = commentRepository.findById(targetCommentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found"));

            comment.setText(commentEditData.getNewText());
            final CommentDto commentDto = commentMapper.toDto(comment);
<<<<<<< HEAD
            return null;
//            return ResponseSuccess.builder()
//                .httpCode(HttpStatus.OK)
//                .message("Comment has been edited")
//                .data(commentDto)
//                .build();
=======
            return OperationResultSuccess.builder()
                .httpCode(HttpStatus.OK)
                .message("Comment has been edited")
                .data(commentDto)
                .build();
>>>>>>> 282c7c2d0776712c32790097da1739769a0824f5
        } catch (ResponseStatusException e) {
            throw e;
        } catch (RuntimeException e) {
            log.error("Cannot edit comment", e);
            throw new CommentException("Cannot edit comment", e);
        }
    }

    @Override
    @Transactional
    @PreAuthorize("@commentServiceSecurity.checkOwnership(#commentDeleteRequest) or hasRole('SUPERUSER')")
    public OperationResult deleteComment(CommentDeleteRequest commentDeleteRequest) {
        log.debug("Deleting comment with id={}", commentDeleteRequest);
        final Long targetCommentId = commentDeleteRequest.getTargetCommentId();

        final Comment comment = commentRepository.findById(targetCommentId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found"));

        commentRepository.delete(comment);
<<<<<<< HEAD
        return null;
//        return ResponseSuccess.builder()
//            .httpCode(HttpStatus.OK)
//            .message("Comment has been deleted")
//            .build();
=======
        return OperationResultSuccess.builder()
            .httpCode(HttpStatus.OK)
            .message("Comment has been deleted")
            .build();
>>>>>>> 282c7c2d0776712c32790097da1739769a0824f5
    }
}
