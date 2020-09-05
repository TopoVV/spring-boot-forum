package com.topov.forum.security.jwt;

import com.topov.forum.security.ForumUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Log4j2
@Service
public class JwtServiceImpl implements JwtService {
    @Value("${jwt.secret}")
    private String secret;

    @PostConstruct
    private void configure() {
        secret = Base64.encodeBase64String(secret.getBytes());
    }

    @Override
    public JwtToken createTokenForUser(ForumUserDetails user) {
        log.debug("Creating token for user: {}", user);
        Date now = new Date();
        String token =  Jwts.builder()
            .setClaims(generateClaims(user))
            .setIssuedAt(now)
            .setSubject(user.getUsername())
            .setExpiration(new Date(now.getTime() + 50*1000*60))
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact();
        return new JwtToken(token);
    }

    private Claims generateClaims(ForumUserDetails user) {
        Claims claims = Jwts.claims();
        final Set<String> authorities = user.getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .collect(toSet());
        claims.put("authorities", authorities);
        claims.put("userId", user.getUserId());
        claims.put("enabled", user.isEnabled());
        return claims;
    }

    @Override
    @SuppressWarnings("unchecked cast")
    public ForumUserDetails extractUser(String token) {
        log.debug("Extracting user from token");
        final Claims claims = Jwts.parser()
            .setSigningKey(secret)
            .parseClaimsJws(token)
            .getBody();
        final String username = claims.getSubject();
        final boolean enabled = Boolean.parseBoolean(claims.get("enabled").toString());
        final long userId = Long.parseLong(claims.get("userId").toString());
        final Set<SimpleGrantedAuthority> authorities = extractAuthorities((List<String>) claims.get("authorities"));
        return new ForumUserDetails(userId, username, "", enabled, authorities);
    }

    private Set<SimpleGrantedAuthority> extractAuthorities(List<String> authorities) {
        return authorities.stream()
            .map(SimpleGrantedAuthority::new)
            .collect(toSet());
    }

    @Override
    public boolean isTokenValid(String token) {
        log.debug("Validation token");
        try {
            final Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            final Date expiresAt = claims.getBody().getExpiration();
            return !expiresAt.before(new Date());
        } catch (RuntimeException e) {
            log.error("Error token validation", e);
            return false;
        }
    }
}


