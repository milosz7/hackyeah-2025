package com.example.Prompt2CodeDemo.controllers;

import com.example.Prompt2CodeDemo.dto.ReportDto;
import com.example.Prompt2CodeDemo.entities.Report.ValidationStatus;
import com.example.Prompt2CodeDemo.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reports")
@Tag(name = "Report Management", description = "API for managing reports and file uploads")
public class ReportController {
    
    @Autowired
    private ReportService reportService;
    
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload a report file", description = "Upload an XLSX report file for validation")
    public ResponseEntity<ReportDto> uploadReport(
            @Parameter(description = "XLSX report file") @RequestParam("file") MultipartFile file,
            @Parameter(description = "Supervised entity ID") @RequestParam("supervisedEntityId") Long supervisedEntityId,
            @Parameter(description = "User ID of the submitter") @RequestParam("submittedByUserId") Long submittedByUserId,
            @Parameter(description = "Reporting period (e.g., Q1_2025)") @RequestParam("reportingPeriod") String reportingPeriod) {
        
        try {
            ReportDto report = reportService.uploadReport(file, supervisedEntityId, submittedByUserId, reportingPeriod);
            return ResponseEntity.status(HttpStatus.CREATED).body(report);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping
    @Operation(summary = "Get all reports", description = "Retrieve all reports with pagination support")
    public ResponseEntity<List<ReportDto>> getAllReports() {
        List<ReportDto> reports = reportService.getAllReports();
        return ResponseEntity.ok(reports);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get report by ID", description = "Retrieve a specific report by its ID")
    public ResponseEntity<ReportDto> getReportById(@PathVariable Long id) {
        Optional<ReportDto> report = reportService.getReportById(id);
        return report.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/entity/{supervisedEntityId}")
    @Operation(summary = "Get reports by entity", description = "Retrieve all reports for a specific supervised entity")
    public ResponseEntity<List<ReportDto>> getReportsByEntity(@PathVariable Long supervisedEntityId) {
        List<ReportDto> reports = reportService.getReportsByEntity(supervisedEntityId);
        return ResponseEntity.ok(reports);
    }
    
    @GetMapping("/status/{status}")
    @Operation(summary = "Get reports by status", description = "Retrieve reports filtered by validation status")
    public ResponseEntity<List<ReportDto>> getReportsByStatus(@PathVariable ValidationStatus status) {
        List<ReportDto> reports = reportService.getReportsByStatus(status);
        return ResponseEntity.ok(reports);
    }
    
    @GetMapping("/period/{reportingPeriod}")
    @Operation(summary = "Get reports by period", description = "Retrieve reports for a specific reporting period")
    public ResponseEntity<List<ReportDto>> getReportsByPeriod(@PathVariable String reportingPeriod) {
        List<ReportDto> reports = reportService.getReportsByPeriod(reportingPeriod);
        return ResponseEntity.ok(reports);
    }
    
    @PutMapping("/{id}/status")
    @Operation(summary = "Update report status", description = "Update the validation status of a report")
    public ResponseEntity<ReportDto> updateReportStatus(
            @PathVariable Long id,
            @Parameter(description = "New validation status") @RequestParam ValidationStatus status,
            @Parameter(description = "Validation errors (optional)") @RequestParam(required = false) String validationErrors) {
        
        Optional<ReportDto> updatedReport = reportService.updateReportStatus(id, status, validationErrors);
        return updatedReport.map(ResponseEntity::ok)
                          .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}/archive")
    @Operation(summary = "Archive report", description = "Archive a report")
    public ResponseEntity<ReportDto> archiveReport(@PathVariable Long id) {
        Optional<ReportDto> archivedReport = reportService.archiveReport(id);
        return archivedReport.map(ResponseEntity::ok)
                           .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}/contest")
    @Operation(summary = "Contest report", description = "Contest a report with description of irregularities")
    public ResponseEntity<ReportDto> contestReport(
            @PathVariable Long id,
            @Parameter(description = "Description of irregularities") @RequestParam String descriptionOfIrregularities) {
        
        Optional<ReportDto> contestedReport = reportService.contestReport(id, descriptionOfIrregularities);
        return contestedReport.map(ResponseEntity::ok)
                            .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/{originalReportId}/correction")
    @Operation(summary = "Create correction", description = "Submit a correction for an existing report")
    public ResponseEntity<ReportDto> createCorrection(
            @PathVariable Long originalReportId,
            @Parameter(description = "Correction file") @RequestParam("file") MultipartFile file,
            @Parameter(description = "Supervised entity ID") @RequestParam("supervisedEntityId") Long supervisedEntityId,
            @Parameter(description = "User ID of the submitter") @RequestParam("submittedByUserId") Long submittedByUserId) {
        
        try {
            ReportDto correction = reportService.createCorrection(originalReportId, file, supervisedEntityId, submittedByUserId);
            return ResponseEntity.status(HttpStatus.CREATED).body(correction);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/{originalReportId}/corrections")
    @Operation(summary = "Get corrections", description = "Retrieve all corrections for a specific report")
    public ResponseEntity<List<ReportDto>> getCorrectionsForReport(@PathVariable Long originalReportId) {
        List<ReportDto> corrections = reportService.getCorrectionsForReport(originalReportId);
        return ResponseEntity.ok(corrections);
    }
    
    @GetMapping("/{id}/download")
    @Operation(summary = "Download report file", description = "Download the original report file")
    public ResponseEntity<byte[]> downloadReportFile(@PathVariable Long id) {
        try {
            byte[] fileContent = reportService.downloadReportFile(id);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "report.xlsx");
            
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(fileContent);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/{id}/validation-result")
    @Operation(summary = "Download validation result", description = "Download the validation result file")
    public ResponseEntity<byte[]> downloadValidationResult(@PathVariable Long id) {
        try {
            byte[] fileContent = reportService.downloadValidationResult(id);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "validation_result.xlsx");
            
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(fileContent);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
