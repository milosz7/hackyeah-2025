package com.example.Prompt2CodeDemo.config;

import com.example.Prompt2CodeDemo.dao.interfaces.SupervisedEntityDao;
import com.example.Prompt2CodeDemo.entities.SupervisedEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.core.annotation.Order;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(2)
public class SupervisedEntityInitializerConfig implements ApplicationRunner {

    private final SupervisedEntityDao supervisedEntityDao;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Initializing supervised entities from CSV...");
        
        initializeSupervisedEntities();
        
        log.info("Supervised entities initialization completed.");
    }

    private void initializeSupervisedEntities() {
        try {
            ClassPathResource resource = new ClassPathResource("supervised_entities.csv");
            
            if (!resource.exists()) {
                log.warn("CSV file 'supervised_entities.csv' not found in classpath. Skipping supervised entities initialization.");
                return;
            }

            List<SupervisedEntity> entities = parseCsvFile(resource);
            
            for (SupervisedEntity entity : entities) {
                if (!supervisedEntityDao.existsByUknfCode(entity.getUknfCode())) {
                    supervisedEntityDao.save(entity);
                    log.info("Created supervised entity: {} ({})", entity.getEntityName(), entity.getUknfCode());
                } else {
                    log.info("Supervised entity already exists: {} ({})", entity.getEntityName(), entity.getUknfCode());
                }
            }
            
            log.info("Processed {} supervised entities from CSV", entities.size());
            
        } catch (Exception e) {
            log.error("Error initializing supervised entities from CSV", e);
        }
    }

    private List<SupervisedEntity> parseCsvFile(ClassPathResource resource) throws Exception {
        List<SupervisedEntity> entities = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            
            String line;
            boolean isFirstLine = true;
            
            while ((line = reader.readLine()) != null) {
                // Skip header line
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                
                // Skip empty lines
                if (line.trim().isEmpty()) {
                    continue;
                }
                
                SupervisedEntity entity = parseCsvLine(line);
                if (entity != null) {
                    entities.add(entity);
                }
            }
        }
        
        return entities;
    }

    private SupervisedEntity parseCsvLine(String line) {
        try {
            String[] fields = line.split(";");
            
            if (fields.length < 17) {
                log.warn("Invalid CSV line (insufficient fields): {}", line);
                return null;
            }
            
            SupervisedEntity entity = new SupervisedEntity();
            
            // Map CSV fields to entity properties
            entity.setEntityType(getFieldValue(fields, 0));
            entity.setUknfCode(getFieldValue(fields, 1));
            entity.setEntityName(getFieldValue(fields, 2));
            entity.setNip(getFieldValue(fields, 3));
            entity.setKrs(getFieldValue(fields, 4));
            entity.setLei(getFieldValue(fields, 5));
            entity.setStreet(getFieldValue(fields, 6));
            entity.setBuildingNumber(getFieldValue(fields, 7));
            entity.setPremisesNumber(getFieldValue(fields, 8));
            entity.setPostalCode(getFieldValue(fields, 9));
            entity.setCity(getFieldValue(fields, 10));
            entity.setPhoneNumber(getFieldValue(fields, 11));
            entity.setEmail(getFieldValue(fields, 12));
            entity.setUknfRegistrationNumber(getFieldValue(fields, 13));
            entity.setEntityStatus(getFieldValue(fields, 14));
            entity.setEntityCategory(getFieldValue(fields, 15));
            entity.setIsCrossBorder(parseBoolean(getFieldValue(fields, 16)));
            
            return entity;
            
        } catch (Exception e) {
            log.error("Error parsing CSV line: {}", line, e);
            return null;
        }
    }

    private String getFieldValue(String[] fields, int index) {
        if (index >= fields.length) {
            return null;
        }
        String value = fields[index].trim();
        return value.isEmpty() ? null : value;
    }

    private Boolean parseBoolean(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return "1".equals(value.trim()) || "true".equalsIgnoreCase(value.trim());
    }
}
