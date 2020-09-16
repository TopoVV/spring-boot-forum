package com.topov.forum.controller.advice;

import com.topov.forum.controller.PostController;
import com.topov.forum.dto.model.post.views.PostViews;
import com.topov.forum.dto.response.ApiResponse;
import com.topov.forum.dto.response.ErrorResponse;
import com.topov.forum.dto.response.post.PostCreateResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;

@RestControllerAdvice(assignableTypes = PostController.class)
public class PostControllerAdvice extends AbstractMappingJacksonResponseBodyAdvice {

    @Override
    protected void beforeBodyWriteInternal(MappingJacksonValue mappingJacksonValue, MediaType mediaType, MethodParameter methodParameter, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        ApiResponse responseObject = (ApiResponse) mappingJacksonValue.getValue();
        mappingJacksonValue.setSerializationView(responseObject.getJsonView());
    }
}
