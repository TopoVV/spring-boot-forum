package com.topov.forum;

import com.topov.forum.model.ForumUser;
import com.topov.forum.model.Role;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class ForumUserDetailsSecurityContextFactory implements WithSecurityContextFactory<WithMockForumUserDetails> {
    @Override
    public SecurityContext createSecurityContext(WithMockForumUserDetails customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        ForumUser forumUser = new ForumUser();
        forumUser.setUsername(customUser.username());
        forumUser.addRole(new Role(Role.Roles.USER));

        ForumUserDetails principal = new ForumUserDetails(forumUser);
        Authentication auth = new UsernamePasswordAuthenticationToken(principal, "", principal.getAuthorities());

        context.setAuthentication(auth);
        return context;
    }
}
