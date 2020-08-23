package com.topov.forum.mapper;

import com.topov.forum.dto.PostDto;
import com.topov.forum.model.Post;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class PostMapper {
    private final ModelMapper mapper;

    @PostConstruct
    private void configure() {
        mapper.addMappings(new PostDtoPropertyMap());
    }

    @Autowired
    public PostMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public PostDto toDto(Post post) {
        return mapper.map(post, PostDto.class);
    }

    private static final class PostDtoPropertyMap extends PropertyMap<Post, PostDto> {
        @Override
        protected void configure() {
            map().setTitle(source.getTitle());
            map().setText(source.getText());
            map().setPostId(source.getPostId());
            map().setAuthor(source.getCreator().getUsername());
        }
    }
}
