package com.topov.forum.initialization;

import com.topov.forum.model.Role;
import com.topov.forum.repository.RoleRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Log4j2
@Component
@Profile("!test")
public class RolesInitialization implements ApplicationRunner {
    private final RoleRepository roleRepository;

    @Autowired
    public RolesInitialization(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        log.debug("Initializations of access roles.");
        roleRepository.saveAll(List.of(
            new Role(Role.Roles.USER),
            new Role(Role.Roles.ADMIN),
            new Role(Role.Roles.SUPERUSER)
        ));
        log.debug("Access roles have been initialized.");
    }
}
