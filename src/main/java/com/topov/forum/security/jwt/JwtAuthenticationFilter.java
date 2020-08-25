package com.topov.forum.security.jwt;

import com.topov.forum.security.ForumUserDetails;
import com.topov.forum.security.ForumUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final ForumUserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Value("${jwt.authorizationHeader}")
    private String authenticationHeader;

    @Autowired
    public JwtAuthenticationFilter(ForumUserDetailsService userDetailsService, JwtService jwtService) {
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorization = request.getHeader(authenticationHeader);
        if (authorization != null && authorization.startsWith("Bearer ")) {
            final String token = authorization.substring(7);
            if(jwtService.isTokenValid(token) && isSecurityContextEmpty()) {
                final ForumUserDetails user = jwtService.extractUser(token);
                final var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean isSecurityContextEmpty() {
        return SecurityContextHolder.getContext().getAuthentication() == null;
    }
}




