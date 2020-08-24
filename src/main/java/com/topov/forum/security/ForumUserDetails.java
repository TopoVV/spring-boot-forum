package com.topov.forum.security;

import com.topov.forum.model.ForumUser;
import com.topov.forum.model.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class ForumUserDetails implements UserDetails {
    private final ForumUser user;

    public ForumUserDetails(ForumUser user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles()
            .stream()
            .map(Role::getRoleName)
            .map(Role.Roles::toString)
            .map("ROLE_"::concat)
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
    }

    @Override
    public String getPassword() { return user.getPassword(); }

    @Override
    public String getUsername() { return user.getUsername(); }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return user.getEnabled(); }

    public Long getUserId() { return user.getUserId(); }
    public Set<Role> getRoles() { return user.getRoles(); }
}
