package com.example.Prompt2CodeDemo.config;

import com.example.Prompt2CodeDemo.dao.interfaces.RoleDao;
import com.example.Prompt2CodeDemo.entities.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class RoleInitializerConfig implements ApplicationRunner {

    private final RoleDao roleDao;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Initializing default roles...");
        
        initializeRoles();
        
        log.info("Default roles initialization completed.");
    }

    private void initializeRoles() {
        List<Role> defaultRoles = Arrays.asList(
            createRole("ADMIN", Arrays.asList("READ", "WRITE", "DELETE", "MANAGE_USERS", "MANAGE_ROLES")),
            createRole("EMPLOYEE", Arrays.asList("READ", "WRITE"))
        );

        for (Role role : defaultRoles) {
            if (!roleDao.existsByName(role.getName())) {
                roleDao.save(role);
                log.info("Created role: {}", role.getName());
            } else {
                log.info("Role already exists: {}", role.getName());
            }
        }
    }

    private Role createRole(String name, List<String> permissions) {
        Role role = new Role();
        role.setName(name);
        role.setPermissions(String.join(",", permissions));
        return role;
    }
}
