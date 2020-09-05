package com.topov.forum.mapper;

import com.topov.forum.dto.model.post.PostDto;
import com.topov.forum.model.Post;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;

@Service
public class PostMapper {
    private final ModelMapper mapper;

    @Autowired
    public PostMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @PostConstruct
    private void configure() {
        mapper.addMappings(new PostToPostDtoPropertyMap());
    }

    public PostDto toDto(Post post) {
        return mapper.map(post, PostDto.class);
    }

    private static final class PostToPostDtoPropertyMap extends PropertyMap<Post, PostDto> {
        @Override
        protected void configure() {
            map().setPostId(source.getPostId());
            map().setTitle(source.getTitle());
            map().setText(source.getText());
            map().setAuthor(source.getCreator().getUsername());
            using(collectionToSizeConverter).map(source.getComments()).setCommentsAmount(null);
            using(collectionToSizeConverter).map(source.getVisits()).setVisitsAmount(null);
        }
    }

    public static final Converter<Collection<?>, Integer> collectionToSizeConverter =
        mappingContext -> mappingContext.getSource().size();
}
