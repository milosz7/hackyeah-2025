package com.example.Prompt2CodeDemo.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "file_name", nullable = false, length = 500)
    private String fileName;
    
    @Column(name = "original_file_name", nullable = false, length = 500)
    private String originalFileName;
    
    @Column(name = "file_path", nullable = false, length = 1000)
    private String filePath;
    
    @Column(name = "file_size", nullable = false)
    private Long fileSize;
    
    @Column(name = "content_type", nullable = false, length = 100)
    private String contentType;
    
    @Column(name = "validation_status", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private ValidationStatus validationStatus = ValidationStatus.WORKING;
    
    @Column(name = "reporting_period", length = 50)
    private String reportingPeriod;
    
    @Column(name = "entity_name", length = 500)
    private String entityName;
    
    @Column(name = "submitted_by", length = 500)
    private String submittedBy;
    
    @Column(name = "submitted_at", nullable = false)
    private LocalDateTime submittedAt;
    
    @Column(name = "validated_at")
    private LocalDateTime validatedAt;
    
    @Column(name = "validation_result_file_path", length = 1000)
    private String validationResultFilePath;
    
    @Column(name = "validation_errors", columnDefinition = "TEXT")
    private String validationErrors;
    
    @Column(name = "is_archived", nullable = false)
    private Boolean isArchived = false;
    
    @Column(name = "is_correction", nullable = false)
    private Boolean isCorrection = false;
    
    @Column(name = "original_report_id")
    private Long originalReportId;
    
    @Column(name = "description_of_irregularities", columnDefinition = "TEXT")
    private String descriptionOfIrregularities;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supervised_entity_id")
    private SupervisedEntity supervisedEntity;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submitted_by_user_id")
    private User submittedByUser;
    
    public enum ValidationStatus {
        WORKING,
        TRANSMITTED,
        ONGOING,
        SUCCESSFUL,
        ERRORS,
        TECHNICAL_ERROR,
        ERROR_EXCEEDED_TIME,
        CONTESTED_BY_UKNF
    }
}
