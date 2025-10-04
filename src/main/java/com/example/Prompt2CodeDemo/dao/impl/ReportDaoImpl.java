package com.example.Prompt2CodeDemo.dao.impl;

import com.example.Prompt2CodeDemo.dao.interfaces.ReportDao;
import com.example.Prompt2CodeDemo.entities.Report;
import com.example.Prompt2CodeDemo.entities.Report.ValidationStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class ReportDaoImpl implements ReportDao {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    @Transactional(readOnly = true)
    public List<Report> findAll() {
        TypedQuery<Report> query = entityManager.createQuery("SELECT r FROM Report r ORDER BY r.submittedAt DESC", Report.class);
        return query.getResultList();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Report> findById(Long id) {
        Report report = entityManager.find(Report.class, id);
        return Optional.ofNullable(report);
    }
    
    @Override
    public Report save(Report report) {
        if (report.getId() == null) {
            entityManager.persist(report);
        } else {
            report = entityManager.merge(report);
        }
        return report;
    }
    
    @Override
    public void deleteById(Long id) {
        Report report = entityManager.find(Report.class, id);
        if (report != null) {
            entityManager.remove(report);
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return entityManager.find(Report.class, id) != null;
    }
    
    @Override
    @Transactional(readOnly = true)
    public long count() {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(r) FROM Report r", Long.class);
        return query.getSingleResult();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Report> findBySupervisedEntityId(Long supervisedEntityId) {
        TypedQuery<Report> query = entityManager.createQuery(
            "SELECT r FROM Report r WHERE r.supervisedEntity.id = :supervisedEntityId ORDER BY r.submittedAt DESC", Report.class);
        query.setParameter("supervisedEntityId", supervisedEntityId);
        return query.getResultList();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Report> findByValidationStatus(ValidationStatus status) {
        TypedQuery<Report> query = entityManager.createQuery(
            "SELECT r FROM Report r WHERE r.validationStatus = :status ORDER BY r.submittedAt DESC", Report.class);
        query.setParameter("status", status);
        return query.getResultList();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Report> findByReportingPeriod(String reportingPeriod) {
        TypedQuery<Report> query = entityManager.createQuery(
            "SELECT r FROM Report r WHERE r.reportingPeriod = :reportingPeriod ORDER BY r.submittedAt DESC", Report.class);
        query.setParameter("reportingPeriod", reportingPeriod);
        return query.getResultList();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Report> findByOriginalReportId(Long originalReportId) {
        TypedQuery<Report> query = entityManager.createQuery(
            "SELECT r FROM Report r WHERE r.originalReportId = :originalReportId ORDER BY r.submittedAt DESC", Report.class);
        query.setParameter("originalReportId", originalReportId);
        return query.getResultList();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Report> findByIsArchived(Boolean isArchived) {
        TypedQuery<Report> query = entityManager.createQuery(
            "SELECT r FROM Report r WHERE r.isArchived = :isArchived ORDER BY r.submittedAt DESC", Report.class);
        query.setParameter("isArchived", isArchived);
        return query.getResultList();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Report> findByIsCorrection(Boolean isCorrection) {
        TypedQuery<Report> query = entityManager.createQuery(
            "SELECT r FROM Report r WHERE r.isCorrection = :isCorrection ORDER BY r.submittedAt DESC", Report.class);
        query.setParameter("isCorrection", isCorrection);
        return query.getResultList();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Report> findBySubmittedByUserId(Long submittedByUserId) {
        TypedQuery<Report> query = entityManager.createQuery(
            "SELECT r FROM Report r WHERE r.submittedByUser.id = :submittedByUserId ORDER BY r.submittedAt DESC", Report.class);
        query.setParameter("submittedByUserId", submittedByUserId);
        return query.getResultList();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Report> findByEntityName(String entityName) {
        TypedQuery<Report> query = entityManager.createQuery(
            "SELECT r FROM Report r WHERE r.entityName = :entityName ORDER BY r.submittedAt DESC", Report.class);
        query.setParameter("entityName", entityName);
        return query.getResultList();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Report> findBySubmittedAtBetween(LocalDateTime startDate, LocalDateTime endDate) {
        TypedQuery<Report> query = entityManager.createQuery(
            "SELECT r FROM Report r WHERE r.submittedAt BETWEEN :startDate AND :endDate ORDER BY r.submittedAt DESC", Report.class);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }
}

