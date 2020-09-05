
package com.topov.forum.security;

import com.topov.forum.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Component
public class ForumUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public ForumUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Fetching user: {}", username);
        return userRepository.findByUsernameWithRoles(username)
            .map(ForumUserDetails::new)
            .orElseThrow(() -> {
                log.error("User {} not found", username);
                return new UsernameNotFoundException("User not found");
            });
    }
}
