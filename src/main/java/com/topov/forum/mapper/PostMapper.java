package com.topov.forum.mapper;

import com.topov.forum.dto.PostDto;
import com.topov.forum.dto.ShortPostDto;
import com.topov.forum.model.Post;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

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
            map().setViews(source.getViews());
        }
    }

    private static final class ShortPostDtoPropertyMap extends PropertyMap<Post, ShortPostDto> {
        @Override
        protected void configure() {
            map().setPostId(source.getPostId());
            map().setTitle(source.getTitle());
            map().setAuthor(source.getCreator().getUsername());
            map().setViews(source.getViews());
        }
    }
}
