package com.example.Prompt2CodeDemo.entities;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
    
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", pesel='" + pesel + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isActive=" + isActive +
                ", userType='" + userType + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", roles=" + roles +
                ", supervisedEntities=" + supervisedEntities +
                '}';
    }
}
