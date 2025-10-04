package com.example.Prompt2CodeDemo.dao.interfaces;

import com.example.Prompt2CodeDemo.entities.Report;
import com.example.Prompt2CodeDemo.entities.Report.ValidationStatus;

import java.util.List;
import java.util.Optional;

public interface ReportDao {
    
    List<Report> findAll();
    
    Optional<Report> findById(Long id);
    
    Report save(Report report);
    
    void deleteById(Long id);
    
    boolean existsById(Long id);
    
    long count();
    
    // Report-specific queries
    List<Report> findBySupervisedEntityId(Long supervisedEntityId);
    
    List<Report> findByValidationStatus(ValidationStatus status);
    
    List<Report> findByReportingPeriod(String reportingPeriod);
    
    List<Report> findByOriginalReportId(Long originalReportId);
    
    List<Report> findByIsArchived(Boolean isArchived);
    
    List<Report> findByIsCorrection(Boolean isCorrection);
    
    List<Report> findBySubmittedByUserId(Long submittedByUserId);
    
    List<Report> findByEntityName(String entityName);
    
    List<Report> findBySubmittedAtBetween(java.time.LocalDateTime startDate, java.time.LocalDateTime endDate);
}


