package com.example.Prompt2CodeDemo.controllers;

import com.example.Prompt2CodeDemo.entities.Report;
import com.example.Prompt2CodeDemo.service.ReportService;
import com.example.Prompt2CodeDemo.service.ValidationService;
import com.example.Prompt2CodeDemo.service.FileStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RestController
@RequestMapping("/api/validation")
@Tag(name = "Validation Management", description = "API for report validation and status management")
public class ValidationController {
    
    @Autowired
    private ValidationService validationService;
    
    @Autowired
    private ReportService reportService;
    
    @Autowired
    private FileStorageService fileStorageService;
    
    @PostMapping("/validate/{reportId}")
    @Operation(summary = "Validate a report", description = "Trigger validation for a specific report")
    public ResponseEntity<?> validateReport(@PathVariable Long reportId) {
        try {
            // Get the report
            Optional<Report> reportOpt = reportService.getReportById(reportId)
                    .map(dto -> {
                        Report report = new Report();
                        report.setId(dto.getId());
                        report.setFileName(dto.getFileName());
                        report.setOriginalFileName(dto.getOriginalFileName());
                        report.setFilePath(dto.getFilePath());
                        report.setFileSize(dto.getFileSize());
                        report.setContentType(dto.getContentType());
                        report.setValidationStatus(dto.getValidationStatus());
                        report.setReportingPeriod(dto.getReportingPeriod());
                        report.setEntityName(dto.getEntityName());
                        report.setSubmittedBy(dto.getSubmittedBy());
                        report.setSubmittedAt(dto.getSubmittedAt());
                        return report;
                    });
            
            if (reportOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            Report report = reportOpt.get();
            
            // Update status to ONGOING
            reportService.updateReportStatus(reportId, Report.ValidationStatus.ONGOING, null);
            
            // Perform validation
            ValidationService.ValidationResult result = validationService.validateReport(report);
            
            // Generate validation result file
            String validationResultContent = validationService.generateValidationResultFile(report, result);
            
            // Store validation result file
            String validationResultPath = storeValidationResultFile(report, validationResultContent);
            
            // Update report with validation results
            String errorMessage = result.hasErrors() ? result.getErrorMessage() : null;
            reportService.updateReportStatus(reportId, result.getStatus(), errorMessage);
            
            // Update validation result file path in report
            updateReportValidationResultPath(reportId, validationResultPath);
            
            return ResponseEntity.ok(new ValidationResponse(
                result.getStatus().toString(),
                result.getValidationDetails(),
                result.hasErrors(),
                result.getErrorMessage(),
                validationResultPath
            ));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Validation failed: " + e.getMessage()));
        }
    }
    
    @GetMapping("/result/{reportId}")
    @Operation(summary = "Get validation result file", description = "Download the validation result file for a report")
    public ResponseEntity<byte[]> getValidationResult(@PathVariable Long reportId) {
        try {
            Optional<Report> reportOpt = reportService.getReportById(reportId)
                    .map(dto -> {
                        Report report = new Report();
                        report.setId(dto.getId());
                        report.setValidationResultFilePath(dto.getValidationResultFilePath());
                        return report;
                    });
            
            if (reportOpt.isEmpty() || reportOpt.get().getValidationResultFilePath() == null) {
                return ResponseEntity.notFound().build();
            }
            
            byte[] fileContent = fileStorageService.loadFileAsBytes(reportOpt.get().getValidationResultFilePath());
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            headers.setContentDispositionFormData("attachment", "validation_result_" + reportId + ".txt");
            
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(fileContent);
                    
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    private String storeValidationResultFile(Report report, String content) throws IOException {
        // Create validation results directory
        String validationDir = "validation-results";
        Path validationPath = Paths.get(validationDir);
        if (!Files.exists(validationPath)) {
            Files.createDirectories(validationPath);
        }
        
        // Generate filename with timestamp
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String filename = String.format("validation_result_%d_%s.txt", report.getId(), timestamp);
        
        // Store file
        Path filePath = validationPath.resolve(filename);
        Files.write(filePath, content.getBytes());
        
        // Return relative path
        return validationPath.relativize(filePath).toString().replace("\\", "/");
    }
    
    private void updateReportValidationResultPath(Long reportId, String validationResultPath) {
        try {
            reportService.updateValidationResultFilePath(reportId, validationResultPath);
        } catch (Exception e) {
            System.err.println("Failed to update validation result path: " + e.getMessage());
        }
    }
    
    // Response classes
    public static class ValidationResponse {
        private final String status;
        private final String details;
        private final boolean hasErrors;
        private final String errorMessage;
        private final String resultFilePath;
        
        public ValidationResponse(String status, String details, boolean hasErrors, String errorMessage, String resultFilePath) {
            this.status = status;
            this.details = details;
            this.hasErrors = hasErrors;
            this.errorMessage = errorMessage;
            this.resultFilePath = resultFilePath;
        }
        
        public String getStatus() { return status; }
        public String getDetails() { return details; }
        public boolean isHasErrors() { return hasErrors; }
        public String getErrorMessage() { return errorMessage; }
        public String getResultFilePath() { return resultFilePath; }
    }
    
    public static class ErrorResponse {
        private final String message;
        
        public ErrorResponse(String message) {
            this.message = message;
        }
        
        public String getMessage() { return message; }
    }
}
