package com.example.Prompt2CodeDemo.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "first_name", nullable = false, length = 250)
    private String firstName;
    
    @Column(name = "last_name", nullable = false, length = 250)
    private String lastName;
    
    @Column(name = "pesel", length = 250)
    private String pesel;
    
    @Column(name = "email", unique = true, nullable = false, length = 500)
    private String email;
    
    @Column(name = "password", columnDefinition = "NVARCHAR(MAX)")
    private String password;
    
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
    
    @Column(name = "user_type", nullable = false, length = 50)
    private String userType;
    
    @Column(name = "phone_number", length = 250)
    private String phoneNumber;
    
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;
    
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "user_supervised_entities",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "supervised_entity_id")
    )
    private Set<SupervisedEntity> supervisedEntities;
}
