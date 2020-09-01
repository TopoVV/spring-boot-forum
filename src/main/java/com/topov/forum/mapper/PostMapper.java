package com.topov.forum.mapper;

import com.topov.forum.dto.CommentDto;
import com.topov.forum.dto.PostDto;
import com.topov.forum.dto.ShortPostDto;
import com.topov.forum.dto.request.post.PostCreateRequest;
import com.topov.forum.model.Comment;
import com.topov.forum.model.Post;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
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
        mapper.addMappings(new PostToPostDtoPropertyMap());
        mapper.addMappings(new PostToShortPostDtoPropertyMap());
    }

    public PostDto toDto(Post post) {
        return mapper.map(post, PostDto.class);
    }
    public ShortPostDto toShortDto(Post post) { return mapper.map(post, ShortPostDto.class); }

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

    private static final class PostToShortPostDtoPropertyMap extends PropertyMap<Post, ShortPostDto> {
        @Override
        protected void configure() {
            map().setPostId(source.getPostId());
            map().setTitle(source.getTitle());
            map().setAuthor(source.getCreator().getUsername());
            using(collectionToSizeConverter).map(source.getComments()).setCommentsAmount(null);
            using(collectionToSizeConverter).map(source.getVisits()).setVisitsAmount(null);
            using(collectionToSizeConverter).map(source.getComments()).setCommentsAmount(null);
        }
    }

    public static final Converter<Collection<?>, Integer> collectionToSizeConverter =
        mappingContext -> mappingContext.getSource().size();
}
