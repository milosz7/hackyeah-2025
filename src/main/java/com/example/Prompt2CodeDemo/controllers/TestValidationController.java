package com.example.Prompt2CodeDemo.controllers;

import com.example.Prompt2CodeDemo.entities.Report;
import com.example.Prompt2CodeDemo.service.ValidationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/test")
@Tag(name = "Test Validation", description = "Test endpoints for validation functionality")
public class TestValidationController {
    
    @Autowired
    private ValidationService validationService;
    
    @PostMapping("/validate-mock")
    @Operation(summary = "Test validation with mock data", description = "Test the validation service with a mock report")
    public ResponseEntity<?> testValidation() {
        // Create a mock report for testing
        Report mockReport = new Report();
        mockReport.setId(1L);
        mockReport.setFileName("test_report.xlsx");
        mockReport.setOriginalFileName("test_report.xlsx");
        mockReport.setFilePath("uploads/test_report.xlsx");
        mockReport.setFileSize(1024L);
        mockReport.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        mockReport.setValidationStatus(Report.ValidationStatus.WORKING);
        mockReport.setReportingPeriod("Q1_2025");
        mockReport.setEntityName("Test Entity Sp. z o.o.");
        mockReport.setSubmittedBy("Test User");
        mockReport.setSubmittedAt(LocalDateTime.now());
        mockReport.setIsArchived(false);
        mockReport.setIsCorrection(false);
        
        try {
            // Perform validation
            ValidationService.ValidationResult result = validationService.validateReport(mockReport);
            
            // Generate validation result file
            String validationResultContent = validationService.generateValidationResultFile(mockReport, result);
            
            return ResponseEntity.ok(new TestValidationResponse(
                result.getStatus().toString(),
                result.getValidationDetails(),
                result.hasErrors(),
                result.getErrorMessage(),
                validationResultContent
            ));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Validation test failed: " + e.getMessage()));
        }
    }
    
    // Response classes
    public static class TestValidationResponse {
        private final String status;
        private final String details;
        private final boolean hasErrors;
        private final String errorMessage;
        private final String validationResultFile;
        
        public TestValidationResponse(String status, String details, boolean hasErrors, String errorMessage, String validationResultFile) {
            this.status = status;
            this.details = details;
            this.hasErrors = hasErrors;
            this.errorMessage = errorMessage;
            this.validationResultFile = validationResultFile;
        }
        
        public String getStatus() { return status; }
        public String getDetails() { return details; }
        public boolean isHasErrors() { return hasErrors; }
        public String getErrorMessage() { return errorMessage; }
        public String getValidationResultFile() { return validationResultFile; }
    }
    
    public static class ErrorResponse {
        private final String message;
        
        public ErrorResponse(String message) {
            this.message = message;
        }
        
        public String getMessage() { return message; }
    }
}
