package com.topov.forum.security;

import lombok.Data;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Configuration
@PropertySource("classpath:jwt.properties")
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private String secret;
    private String authorizationHeader;
    private String prefix;
}
