package com.example.Prompt2CodeDemo.dto;

import com.example.Prompt2CodeDemo.entities.Report.ValidationStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportDto {
    
    private Long id;
    private String fileName;
    private String originalFileName;
    private String filePath;
    private Long fileSize;
    private String contentType;
    private ValidationStatus validationStatus;
    private String reportingPeriod;
    private String entityName;
    private String submittedBy;
    private LocalDateTime submittedAt;
    private LocalDateTime validatedAt;
    private String validationResultFilePath;
    private String validationErrors;
    private Boolean isArchived;
    private Boolean isCorrection;
    private Long originalReportId;
    private String descriptionOfIrregularities;
    private Long supervisedEntityId;
    private Long submittedByUserId;
}


