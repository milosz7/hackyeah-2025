package com.example.Prompt2CodeDemo.service.impl;

import com.example.Prompt2CodeDemo.dao.interfaces.ReportDao;
import com.example.Prompt2CodeDemo.dto.ReportDto;
import com.example.Prompt2CodeDemo.entities.Report;
import com.example.Prompt2CodeDemo.entities.Report.ValidationStatus;
import com.example.Prompt2CodeDemo.entities.SupervisedEntity;
import com.example.Prompt2CodeDemo.entities.User;
import com.example.Prompt2CodeDemo.mapper.ReportMapper;
import com.example.Prompt2CodeDemo.service.FileStorageService;
import com.example.Prompt2CodeDemo.service.ReportService;
import com.example.Prompt2CodeDemo.service.SupervisedEntityService;
import com.example.Prompt2CodeDemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReportServiceImpl implements ReportService {
    
    @Autowired
    private ReportDao reportDao;
    
    @Autowired
    private ReportMapper reportMapper;
    
    @Autowired
    private FileStorageService fileStorageService;
    
    @Autowired
    private SupervisedEntityService supervisedEntityService;
    
    @Autowired
    private UserService userService;
    
    @Override
    public ReportDto uploadReport(MultipartFile file, Long supervisedEntityId, Long submittedByUserId, String reportingPeriod) {
        try {
            // Store file
            String filePath = fileStorageService.storeFile(file);
            
            // Get supervised entity
            SupervisedEntity supervisedEntity = supervisedEntityService.getSupervisedEntityById(supervisedEntityId)
                    .map(dto -> {
                        SupervisedEntity entity = new SupervisedEntity();
                        entity.setId(dto.getId());
                        entity.setEntityName(dto.getEntityName());
                        return entity;
                    })
                    .orElseThrow(() -> new IllegalArgumentException("Supervised entity not found"));
            
            // Get user - create a minimal User entity for the relationship
            User submittedByUser = new User();
            submittedByUser.setId(submittedByUserId);
            
            // Create report entity
            Report report = new Report();
            report.setFileName(file.getOriginalFilename());
            report.setOriginalFileName(file.getOriginalFilename());
            report.setFilePath(filePath);
            report.setFileSize(file.getSize());
            report.setContentType(file.getContentType());
            report.setValidationStatus(ValidationStatus.WORKING);
            report.setReportingPeriod(reportingPeriod);
            report.setEntityName(supervisedEntity.getEntityName());
            report.setSubmittedBy("User ID: " + submittedByUserId);
            report.setSubmittedAt(LocalDateTime.now());
            report.setSupervisedEntity(supervisedEntity);
            report.setSubmittedByUser(submittedByUser);
            report.setIsArchived(false);
            report.setIsCorrection(false);
            
            // Save report
            Report savedReport = reportDao.save(report);
            
            // Update status to TRANSMITTED after successful save
            savedReport.setValidationStatus(ValidationStatus.TRANSMITTED);
            savedReport = reportDao.save(savedReport);
            
            return reportMapper.toDto(savedReport);
            
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file: " + e.getMessage(), e);
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ReportDto> getAllReports() {
        return reportDao.findAll()
                .stream()
                .map(reportMapper::toDto)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<ReportDto> getReportById(Long id) {
        return reportDao.findById(id)
                .map(reportMapper::toDto);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ReportDto> getReportsByEntity(Long supervisedEntityId) {
        return reportDao.findBySupervisedEntityId(supervisedEntityId)
                .stream()
                .map(reportMapper::toDto)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ReportDto> getReportsByStatus(ValidationStatus status) {
        return reportDao.findByValidationStatus(status)
                .stream()
                .map(reportMapper::toDto)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ReportDto> getReportsByPeriod(String reportingPeriod) {
        return reportDao.findByReportingPeriod(reportingPeriod)
                .stream()
                .map(reportMapper::toDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<ReportDto> updateReportStatus(Long id, ValidationStatus status, String validationErrors) {
        return reportDao.findById(id)
                .map(report -> {
                    report.setValidationStatus(status);
                    report.setValidatedAt(LocalDateTime.now());
                    if (validationErrors != null) {
                        report.setValidationErrors(validationErrors);
                    }
                    Report updatedReport = reportDao.save(report);
                    return reportMapper.toDto(updatedReport);
                });
    }
    
    @Override
    public Optional<ReportDto> archiveReport(Long id) {
        return reportDao.findById(id)
                .map(report -> {
                    report.setIsArchived(true);
                    Report updatedReport = reportDao.save(report);
                    return reportMapper.toDto(updatedReport);
                });
    }
    
    @Override
    public Optional<ReportDto> contestReport(Long id, String descriptionOfIrregularities) {
        return reportDao.findById(id)
                .map(report -> {
                    report.setValidationStatus(ValidationStatus.CONTESTED_BY_UKNF);
                    report.setDescriptionOfIrregularities(descriptionOfIrregularities);
                    report.setValidatedAt(LocalDateTime.now());
                    Report updatedReport = reportDao.save(report);
                    return reportMapper.toDto(updatedReport);
                });
    }
    
    @Override
    public ReportDto createCorrection(Long originalReportId, MultipartFile file, Long supervisedEntityId, Long submittedByUserId) {
        // Get original report
        Report originalReport = reportDao.findById(originalReportId)
                .orElseThrow(() -> new IllegalArgumentException("Original report not found"));
        
        // Upload correction file
        ReportDto correctionReport = uploadReport(file, supervisedEntityId, submittedByUserId, originalReport.getReportingPeriod());
        
        // Update correction report to link to original
        Report correction = reportDao.findById(correctionReport.getId())
                .orElseThrow(() -> new RuntimeException("Failed to create correction report"));
        
        correction.setIsCorrection(true);
        correction.setOriginalReportId(originalReportId);
        correction = reportDao.save(correction);
        
        return reportMapper.toDto(correction);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ReportDto> getCorrectionsForReport(Long originalReportId) {
        return reportDao.findByOriginalReportId(originalReportId)
                .stream()
                .map(reportMapper::toDto)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public byte[] downloadReportFile(Long reportId) {
        Report report = reportDao.findById(reportId)
                .orElseThrow(() -> new IllegalArgumentException("Report not found"));
        
        try {
            return fileStorageService.loadFileAsBytes(report.getFilePath());
        } catch (IOException e) {
            throw new RuntimeException("Failed to download file: " + e.getMessage(), e);
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public byte[] downloadValidationResult(Long reportId) {
        Report report = reportDao.findById(reportId)
                .orElseThrow(() -> new IllegalArgumentException("Report not found"));
        
        if (report.getValidationResultFilePath() == null) {
            throw new IllegalArgumentException("Validation result file not available");
        }
        
        try {
            return fileStorageService.loadFileAsBytes(report.getValidationResultFilePath());
        } catch (IOException e) {
            throw new RuntimeException("Failed to download validation result: " + e.getMessage(), e);
        }
    }
}
