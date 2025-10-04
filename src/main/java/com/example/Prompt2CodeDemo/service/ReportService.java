package com.example.Prompt2CodeDemo.service;

import com.example.Prompt2CodeDemo.dto.ReportDto;
import com.example.Prompt2CodeDemo.entities.Report.ValidationStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ReportService {
    
    ReportDto uploadReport(MultipartFile file, Long supervisedEntityId, Long submittedByUserId, String reportingPeriod);
    
    List<ReportDto> getAllReports();
    
    Optional<ReportDto> getReportById(Long id);
    
    List<ReportDto> getReportsByEntity(Long supervisedEntityId);
    
    List<ReportDto> getReportsByStatus(ValidationStatus status);
    
    List<ReportDto> getReportsByPeriod(String reportingPeriod);
    
    Optional<ReportDto> updateReportStatus(Long id, ValidationStatus status, String validationErrors);
    
    Optional<ReportDto> archiveReport(Long id);
    
    Optional<ReportDto> contestReport(Long id, String descriptionOfIrregularities);
    
    ReportDto createCorrection(Long originalReportId, MultipartFile file, Long supervisedEntityId, Long submittedByUserId);
    
    List<ReportDto> getCorrectionsForReport(Long originalReportId);
    
    byte[] downloadReportFile(Long reportId);
    
    byte[] downloadValidationResult(Long reportId);
}


