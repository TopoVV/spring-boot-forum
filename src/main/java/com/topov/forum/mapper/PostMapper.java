package com.topov.forum.mapper;

import com.topov.forum.dto.CommentDto;
import com.topov.forum.dto.PostDto;
import com.topov.forum.dto.ShortPostDto;
import com.topov.forum.model.Comment;
import com.topov.forum.model.Post;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class PostMapper {
    private final ModelMapper mapper;

    @Autowired
    public PostMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @PostConstruct
    private void configure() {
        mapper.addMappings(new PostDtoPropertyMap());
        mapper.addMappings(new ShortPostDtoPropertyMap());
    }

    public PostDto toDto(Post post) {
        return mapper.map(post, PostDto.class);
    }

    public ShortPostDto toShortDto(Post post) {
        return mapper.map(post, ShortPostDto.class);
    }

    private static final class PostDtoPropertyMap extends PropertyMap<Post, PostDto> {
        @Override
        protected void configure() {
            map().setPostId(source.getPostId());
            map().setTitle(source.getTitle());
            map().setText(source.getText());
            map().setAuthor(source.getCreator().getUsername());
            map().setViews(source.getViews().getCount());
//            map().setCommentsAmount(source.getCommentsAmount().get());
            using(commentListConverter).map(source.getComments()).setComments(null);
        }
    }

    private static final class ShortPostDtoPropertyMap extends PropertyMap<Post, ShortPostDto> {
        @Override
        protected void configure() {
            map().setPostId(source.getPostId());
            map().setTitle(source.getTitle());
            map().setViews(source.getViews().getCount());
            map().setAuthor(source.getCreator().getUsername());
//            map().setCommentsAmount(source.getCommentsAmount().get());
        }
    }

    public static final Converter<List<Comment>, List<CommentDto>>  commentListConverter =
        mappingContext -> mappingContext.getSource()
        .stream()
        .map(comment -> {
            CommentDto commentDto = new CommentDto();
            commentDto.setText(comment.getText());
            commentDto.setCommentId(comment.getCommentId());
            commentDto.setAuthor(comment.getCreator().getUsername());
            return commentDto;
        })
        .collect(toList());
}
