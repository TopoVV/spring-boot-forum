package com.topov.forum.mapper;

import com.topov.forum.dto.model.CommentDto;
import com.topov.forum.model.Comment;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class CommentMapper {
    private final ModelMapper mapper;

    @Autowired
    public CommentMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @PostConstruct
    void configure() {
        mapper.addMappings(new CommentDtoPropertyMap());
    }

    public CommentDto toDto(Comment comment) {
        return mapper.map(comment, CommentDto.class);
    }

    private static class CommentDtoPropertyMap extends PropertyMap<Comment, CommentDto> {
        @Override
        protected void configure() {
            map().setText(source.getText());
            map().setCommentId(source.getCommentId());
            map().setAuthor(source.getCreator().getUsername());
        }
    }
}
