package com.example.Prompt2CodeDemo.dao.impl;

import com.example.Prompt2CodeDemo.dao.interfaces.UserDao;
import com.example.Prompt2CodeDemo.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u", User.class);
        return query.getResultList();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        User user = entityManager.find(User.class, id);
        return Optional.ofNullable(user);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        TypedQuery<User> query = entityManager.createQuery(
            "SELECT u FROM User u WHERE u.email = :email", User.class);
        query.setParameter("email", email);
        List<User> users = query.getResultList();
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }
    
    @Override
    public User save(User user) {
        if (user.getId() == null) {
            entityManager.persist(user);
        } else {
            user = entityManager.merge(user);
        }
        return user;
    }
    
    @Override
    public void deleteById(Long id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            entityManager.remove(user);
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return entityManager.find(User.class, id) != null;
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        TypedQuery<Long> query = entityManager.createQuery(
            "SELECT COUNT(u) FROM User u WHERE u.email = :email", Long.class);
        query.setParameter("email", email);
        return query.getSingleResult() > 0;
    }
    
    @Override
    @Transactional(readOnly = true)
    public long count() {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(u) FROM User u", Long.class);
        return query.getSingleResult();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<User> findByUserType(String userType) {
        TypedQuery<User> query = entityManager.createQuery(
            "SELECT u FROM User u WHERE u.userType = :userType", User.class);
        query.setParameter("userType", userType);
        return query.getResultList();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<User> findByIsActive(Boolean isActive) {
        TypedQuery<User> query = entityManager.createQuery(
            "SELECT u FROM User u WHERE u.isActive = :isActive", User.class);
        query.setParameter("isActive", isActive);
        return query.getResultList();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<User> findByRolesId(Integer roleId) {
        TypedQuery<User> query = entityManager.createQuery(
            "SELECT u FROM User u JOIN u.roles r WHERE r.id = :roleId", User.class);
        query.setParameter("roleId", roleId);
        return query.getResultList();
    }
}
