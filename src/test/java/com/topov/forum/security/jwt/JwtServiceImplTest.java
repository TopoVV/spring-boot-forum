package com.topov.forum.security.jwt;

import com.topov.forum.security.ForumUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtServiceImplTest {
    private final JwtService jwtService;

    @Value("${jwt.secret}")
    private String secret;

    @PostConstruct
    private void configure() {
        secret = Base64.encodeBase64String(secret.getBytes());
    }

    @Autowired
    JwtServiceImplTest(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Test
    public void isTokenValid() {
        final Date now = new Date();
        final Date expiresAt = new Date(now.getTime() + 50 * 1000 * 60);

        Claims claims = Jwts.claims();
        final Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        claims.put("authorities", authorities);
        claims.put("userId", 1L);
        claims.put("enabled", true);

//        String secret = "mykey";

        String token =  Jwts.builder()
            .setClaims(claims)
            .setSubject("username")
            .setIssuedAt(now)
            .setExpiration(expiresAt)
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact();

        final boolean tokenValid = jwtService.isTokenValid(token);
        assertTrue(tokenValid);
    }
}
