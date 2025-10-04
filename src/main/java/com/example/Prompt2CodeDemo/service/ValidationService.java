package com.example.Prompt2CodeDemo.service;

import com.example.Prompt2CodeDemo.entities.Report;


public interface ValidationService {
    
    /**
     * Validate a report and return validation result
     * @param report the report to validate
     * @return validation result containing status and details
     */
    ValidationResult validateReport(Report report);
    
    /**
     * Generate validation result file content
     * @param report the report that was validated
     * @param result the validation result
     * @return validation result file content as string
     */
    String generateValidationResultFile(Report report, ValidationResult result);
    
    /**
     * Validation result data class
     */
    class ValidationResult {
        private final Report.ValidationStatus status;
        private final String errorMessage;
        private final String validationDetails;
        private final boolean hasErrors;
        
        public ValidationResult(Report.ValidationStatus status, String errorMessage, String validationDetails, boolean hasErrors) {
            this.status = status;
            this.errorMessage = errorMessage;
            this.validationDetails = validationDetails;
            this.hasErrors = hasErrors;
        }
        
        public Report.ValidationStatus getStatus() { return status; }
        public String getErrorMessage() { return errorMessage; }
        public String getValidationDetails() { return validationDetails; }
        public boolean hasErrors() { return hasErrors; }
    }
}
