package com.topov.forum;

import com.topov.forum.model.ForumUser;
import com.topov.forum.model.Role;
import com.topov.forum.security.ForumUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import static com.topov.forum.model.Role.Roles;

public class ForumUserDetailsSecurityContextFactory implements WithSecurityContextFactory<WithMockForumUserDetails> {
    @Override
    public SecurityContext createSecurityContext(WithMockForumUserDetails customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        ForumUser forumUser = new ForumUser();
        forumUser.setUserId(customUser.id());
        forumUser.setUsername(customUser.username());
        forumUser.setPassword("");
        forumUser.setEmail("email@email.com");
        forumUser.setEnabled(true);
        forumUser.addRole(new Role(Roles.USER));

        ForumUserDetails principal = new ForumUserDetails(forumUser);
        Authentication auth = new UsernamePasswordAuthenticationToken(principal, "", principal.getAuthorities());

        context.setAuthentication(auth);
        return context;
    }
}
