package com.topov.forum.security;

import com.topov.forum.model.ForumUser;
import com.topov.forum.model.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.topov.forum.model.Role.Roles;

public class ForumUserDetails implements UserDetails {
    private final long userId;
    private final Set<GrantedAuthority> authorities;
    private final String username;
    private final String password;
    private final boolean isEnabled;


    public ForumUserDetails(ForumUser user) {
        this.authorities = new HashSet<>();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.userId = user.getUserId();
        this.isEnabled = user.getEnabled();

        final Set<SimpleGrantedAuthority> roles = user.getRoles()
            .stream()
            .map(Role::getRoleName)
            .map(Roles::toString)
            .map("ROLE_"::concat)
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toSet());
        authorities.addAll(roles);
    }

    public ForumUserDetails(long userId, String username, String password, boolean isEnabled, Set<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.isEnabled = isEnabled;
        this.authorities = Set.copyOf(authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() { return this.password; }

    @Override
    public String getUsername() { return this.username; }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return this.isEnabled; }

    public Long getUserId() { return this.userId; }
}
