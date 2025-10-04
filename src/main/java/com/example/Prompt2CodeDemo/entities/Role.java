package com.example.Prompt2CodeDemo.entities;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Role {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "name", nullable = false, unique = true, length = 250)
    private String name;
    
    @Column(name = "permissions", columnDefinition = "NVARCHAR(MAX)")
    private String permissions;
    
    @ManyToMany(mappedBy = "roles", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<User> users;
    
    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", permissions='" + permissions + '\'' +
                ", users=" + users +
                '}';
    }
}
