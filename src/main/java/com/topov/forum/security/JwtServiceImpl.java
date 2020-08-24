package com.topov.forum.security;

import com.topov.forum.model.ForumUser;
import com.topov.forum.model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

import static com.topov.forum.model.Role.*;
import static java.util.stream.Collectors.toSet;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${jwt.secret}")
    private String secret;

    @Override
    public JwtToken createTokenForUser(ForumUserDetails user) {
        Date now = new Date();
        String token =  Jwts.builder()
            .signWith(SignatureAlgorithm.HS256, secret)
            .setIssuedAt(now)
            .setSubject(user.getUsername())
            .setClaims(generateClaims(user))
            .setExpiration(new Date(now.getTime() + 1000*60))
            .compact();
        return new JwtToken(token);
    }

    private Claims generateClaims(ForumUserDetails user) {
        final Set<String> roles = user.getRoles()
            .stream()
            .map(Role::getRoleName)
            .map(Roles::toString)
            .collect(toSet());
        Claims claims = Jwts.claims();
        claims.put("roles", roles);
        claims.put("id", user.getUserId());
        return claims;
    }
}
