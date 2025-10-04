package com.example.Prompt2CodeDemo.config;

import com.example.Prompt2CodeDemo.dao.interfaces.RoleDao;
import com.example.Prompt2CodeDemo.dao.interfaces.SupervisedEntityDao;
import com.example.Prompt2CodeDemo.dao.interfaces.UserDao;
import com.example.Prompt2CodeDemo.entities.Role;
import com.example.Prompt2CodeDemo.entities.SupervisedEntity;
import com.example.Prompt2CodeDemo.entities.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(3)
public class UserInitializerConfig implements ApplicationRunner {

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final SupervisedEntityDao supervisedEntityDao;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        log.info("Initializing users...");
        
        initializeUsers();
        
        log.info("Users initialization completed.");
    }
    @Transactional 
    private void initializeUsers() {
        // Create admin user
        createAdminUser();
        
        // Create employee users for each supervised entity
        createEmployeeUsers();
    }

    private void createAdminUser() {
        if (!userDao.existsByEmail("admin@system.com")) {
            User adminUser = new User();
            adminUser.setFirstName("System");
            adminUser.setLastName("Administrator");
            adminUser.setEmail("admin@system.com");
            adminUser.setPassword(passwordEncoder.encode("admin123"));
            adminUser.setUserType("EXTERNAL");
            adminUser.setIsActive(true);
            adminUser.setPhoneNumber("+48123456789");
            
            // Assign ADMIN role
            Role adminRole = roleDao.findByName("ADMIN").orElse(null);
            log.info("Admin role: {}", adminRole);
            if (adminRole != null) {
                Set<Role> roles = new HashSet<>();
                roles.add(adminRole);
                adminUser.setRoles(roles);
            }
            
            userDao.save(adminUser);
            log.info("Created admin user: {}", adminUser.getEmail());
        } else {
            log.info("Admin user already exists");
        }
    }

    private void createEmployeeUsers() {
        List<SupervisedEntity> supervisedEntities = supervisedEntityDao.findAll();
        
        for (SupervisedEntity entity : supervisedEntities) {
            createEmployeesForEntity(entity);
        }
    }

    private void createEmployeesForEntity(SupervisedEntity entity) {
        Role employeeRole = roleDao.findByName("EMPLOYEE").orElse(null);
        if (employeeRole == null) {
            log.warn("EMPLOYEE role not found, skipping employee creation for entity: {}", entity.getEntityName());
            return;
        }

        for (int i = 1; i <= 5; i++) {
            String email = String.format("employee%d@%s.com", i, entity.getUknfCode().toLowerCase());
            
            if (!userDao.existsByEmail(email)) {
                User employee = new User();
                employee.setFirstName("Employee" + i);
                employee.setLastName("User");
                employee.setEmail(email);
                employee.setPassword(passwordEncoder.encode("employee123"));
                employee.setUserType("EXTERNAL");
                employee.setIsActive(true);
                employee.setPhoneNumber("+4812345678" + i);
                
                // Assign EMPLOYEE role
                Set<Role> roles = new HashSet<>();
                roles.add(employeeRole);
                employee.setRoles(roles);
                
                // Assign to supervised entity
                Set<SupervisedEntity> supervisedEntities = new HashSet<>();
                supervisedEntities.add(entity);
                employee.setSupervisedEntities(supervisedEntities);
                
                userDao.save(employee);
                log.info("Created employee user: {} for entity: {}", email, entity.getEntityName());
            } else {
                log.info("Employee user already exists: {}", email);
            }
        }
    }
}
