package com.example.Prompt2CodeDemo.dao.impl;

import com.example.Prompt2CodeDemo.dao.interfaces.RoleDao;
import com.example.Prompt2CodeDemo.entities.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class RoleDaoImpl implements RoleDao {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    @Transactional(readOnly = true)
    public List<Role> findAll() {
        TypedQuery<Role> query = entityManager.createQuery("SELECT r FROM Role r", Role.class);
        return query.getResultList();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Role> findById(Integer id) {
        Role role = entityManager.find(Role.class, id);
        return Optional.ofNullable(role);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Role> findByName(String name) {
        TypedQuery<Role> query = entityManager.createQuery(
            "SELECT r FROM Role r WHERE r.name = :name", Role.class);
        query.setParameter("name", name);
        List<Role> roles = query.getResultList();
        return roles.isEmpty() ? Optional.empty() : Optional.of(roles.get(0));
    }
    
    @Override
    public Role save(Role role) {
        if (role.getId() == null) {
            entityManager.persist(role);
        } else {
            role = entityManager.merge(role);
        }
        return role;
    }
    
    @Override
    public void deleteById(Integer id) {
        Role role = entityManager.find(Role.class, id);
        if (role != null) {
            entityManager.remove(role);
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Integer id) {
        return entityManager.find(Role.class, id) != null;
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        TypedQuery<Long> query = entityManager.createQuery(
            "SELECT COUNT(r) FROM Role r WHERE r.name = :name", Long.class);
        query.setParameter("name", name);
        return query.getSingleResult() > 0;
    }
    
    @Override
    @Transactional(readOnly = true)
    public long count() {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(r) FROM Role r", Long.class);
        return query.getSingleResult();
    }
}
