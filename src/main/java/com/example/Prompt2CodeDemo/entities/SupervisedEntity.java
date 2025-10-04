package com.example.Prompt2CodeDemo.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "supervised_entities")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupervisedEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "entity_type", length = 250)
    private String entityType;
    
    @Column(name = "uknf_code", length = 250)
    private String uknfCode;
    
    @Column(name = "entity_name", length = 500)
    private String entityName;
    
    @Column(name = "lei", length = 20)
    private String lei;
    
    @Column(name = "nip", length = 10)
    private String nip;
    
    @Column(name = "krs", length = 10)
    private String krs;
    
    @Column(name = "street", length = 250)
    private String street;
    
    @Column(name = "building_number", length = 250)
    private String buildingNumber;
    
    @Column(name = "premises_number", length = 250)
    private String premisesNumber;
    
    @Column(name = "postal_code", length = 250)
    private String postalCode;
    
    @Column(name = "city", length = 250)
    private String city;
    
    @Column(name = "phone_number", length = 250)
    private String phoneNumber;
    
    @Column(name = "email", length = 500)
    private String email;
    
    @Column(name = "uknf_registration_number", length = 100)
    private String uknfRegistrationNumber;
    
    @Column(name = "entity_status", length = 250)
    private String entityStatus;
    
    @Column(name = "entity_category", length = 500)
    private String entityCategory;
    
    @Column(name = "operator_sector", length = 500)
    private String operatorSector;
    
    @Column(name = "entity_subsector", length = 500)
    private String entitySubsector;
    
    @Column(name = "is_cross_border")
    private Boolean isCrossBorder;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @ManyToMany(mappedBy = "supervisedEntities", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<User> users;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
