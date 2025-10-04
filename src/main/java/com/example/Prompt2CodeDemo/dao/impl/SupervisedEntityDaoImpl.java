package com.example.Prompt2CodeDemo.dao.impl;

import com.example.Prompt2CodeDemo.dao.interfaces.SupervisedEntityDao;
import com.example.Prompt2CodeDemo.entities.SupervisedEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class SupervisedEntityDaoImpl implements SupervisedEntityDao {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    @Transactional(readOnly = true)
    public List<SupervisedEntity> findAll() {
        TypedQuery<SupervisedEntity> query = entityManager.createQuery("SELECT se FROM SupervisedEntity se", SupervisedEntity.class);
        return query.getResultList();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<SupervisedEntity> findById(Long id) {
        SupervisedEntity entity = entityManager.find(SupervisedEntity.class, id);
        return Optional.ofNullable(entity);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<SupervisedEntity> findByUknfCode(String uknfCode) {
        TypedQuery<SupervisedEntity> query = entityManager.createQuery(
            "SELECT se FROM SupervisedEntity se WHERE se.uknfCode = :uknfCode", SupervisedEntity.class);
        query.setParameter("uknfCode", uknfCode);
        List<SupervisedEntity> entities = query.getResultList();
        return entities.isEmpty() ? Optional.empty() : Optional.of(entities.get(0));
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<SupervisedEntity> findByNip(String nip) {
        TypedQuery<SupervisedEntity> query = entityManager.createQuery(
            "SELECT se FROM SupervisedEntity se WHERE se.nip = :nip", SupervisedEntity.class);
        query.setParameter("nip", nip);
        List<SupervisedEntity> entities = query.getResultList();
        return entities.isEmpty() ? Optional.empty() : Optional.of(entities.get(0));
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<SupervisedEntity> findByKrs(String krs) {
        TypedQuery<SupervisedEntity> query = entityManager.createQuery(
            "SELECT se FROM SupervisedEntity se WHERE se.krs = :krs", SupervisedEntity.class);
        query.setParameter("krs", krs);
        List<SupervisedEntity> entities = query.getResultList();
        return entities.isEmpty() ? Optional.empty() : Optional.of(entities.get(0));
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<SupervisedEntity> findByLei(String lei) {
        TypedQuery<SupervisedEntity> query = entityManager.createQuery(
            "SELECT se FROM SupervisedEntity se WHERE se.lei = :lei", SupervisedEntity.class);
        query.setParameter("lei", lei);
        List<SupervisedEntity> entities = query.getResultList();
        return entities.isEmpty() ? Optional.empty() : Optional.of(entities.get(0));
    }
    
    @Override
    public SupervisedEntity save(SupervisedEntity supervisedEntity) {
        if (supervisedEntity.getId() == null) {
            entityManager.persist(supervisedEntity);
        } else {
            supervisedEntity = entityManager.merge(supervisedEntity);
        }
        return supervisedEntity;
    }
    
    @Override
    public void deleteById(Long id) {
        SupervisedEntity entity = entityManager.find(SupervisedEntity.class, id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return entityManager.find(SupervisedEntity.class, id) != null;
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsByUknfCode(String uknfCode) {
        TypedQuery<Long> query = entityManager.createQuery(
            "SELECT COUNT(se) FROM SupervisedEntity se WHERE se.uknfCode = :uknfCode", Long.class);
        query.setParameter("uknfCode", uknfCode);
        return query.getSingleResult() > 0;
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsByNip(String nip) {
        TypedQuery<Long> query = entityManager.createQuery(
            "SELECT COUNT(se) FROM SupervisedEntity se WHERE se.nip = :nip", Long.class);
        query.setParameter("nip", nip);
        return query.getSingleResult() > 0;
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsByKrs(String krs) {
        TypedQuery<Long> query = entityManager.createQuery(
            "SELECT COUNT(se) FROM SupervisedEntity se WHERE se.krs = :krs", Long.class);
        query.setParameter("krs", krs);
        return query.getSingleResult() > 0;
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsByLei(String lei) {
        TypedQuery<Long> query = entityManager.createQuery(
            "SELECT COUNT(se) FROM SupervisedEntity se WHERE se.lei = :lei", Long.class);
        query.setParameter("lei", lei);
        return query.getSingleResult() > 0;
    }
    
    @Override
    @Transactional(readOnly = true)
    public long count() {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(se) FROM SupervisedEntity se", Long.class);
        return query.getSingleResult();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<SupervisedEntity> findByEntityStatus(String entityStatus) {
        TypedQuery<SupervisedEntity> query = entityManager.createQuery(
            "SELECT se FROM SupervisedEntity se WHERE se.entityStatus = :entityStatus", SupervisedEntity.class);
        query.setParameter("entityStatus", entityStatus);
        return query.getResultList();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<SupervisedEntity> findByEntityType(String entityType) {
        TypedQuery<SupervisedEntity> query = entityManager.createQuery(
            "SELECT se FROM SupervisedEntity se WHERE se.entityType = :entityType", SupervisedEntity.class);
        query.setParameter("entityType", entityType);
        return query.getResultList();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<SupervisedEntity> findByIsCrossBorder(Boolean isCrossBorder) {
        TypedQuery<SupervisedEntity> query = entityManager.createQuery(
            "SELECT se FROM SupervisedEntity se WHERE se.isCrossBorder = :isCrossBorder", SupervisedEntity.class);
        query.setParameter("isCrossBorder", isCrossBorder);
        return query.getResultList();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<SupervisedEntity> findByUsersId(Long userId) {
        TypedQuery<SupervisedEntity> query = entityManager.createQuery(
            "SELECT se FROM SupervisedEntity se JOIN se.users u WHERE u.id = :userId", SupervisedEntity.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<SupervisedEntity> findByCity(String city) {
        TypedQuery<SupervisedEntity> query = entityManager.createQuery(
            "SELECT se FROM SupervisedEntity se WHERE se.city = :city", SupervisedEntity.class);
        query.setParameter("city", city);
        return query.getResultList();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<SupervisedEntity> findByEntityCategory(String entityCategory) {
        TypedQuery<SupervisedEntity> query = entityManager.createQuery(
            "SELECT se FROM SupervisedEntity se WHERE se.entityCategory = :entityCategory", SupervisedEntity.class);
        query.setParameter("entityCategory", entityCategory);
        return query.getResultList();
    }
}
