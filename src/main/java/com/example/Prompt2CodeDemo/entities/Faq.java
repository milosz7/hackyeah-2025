package com.example.Prompt2CodeDemo.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "faqs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Faq {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "title", nullable = false, length = 500)
    private String title;
    
    @Column(name = "content", columnDefinition = "NVARCHAR(MAX)")
    private String content;
    
    @Column(name = "category", length = 250)
    private String category;
    
    @ElementCollection
    @CollectionTable(name = "faq_labels", joinColumns = @JoinColumn(name = "faq_id"))
    @Column(name = "label")
    private List<String> labels;
    
    @Column(name = "date_added", nullable = false)
    private LocalDateTime dateAdded;
    
    @Column(name = "status", nullable = false, length = 50)
    private String status; // PENDING, ANSWERED, PUBLISHED, ARCHIVED
    
    @Column(name = "answer", columnDefinition = "NVARCHAR(MAX)")
    private String answer;
    
    @Column(name = "rating")
    private Integer rating; // 1-5 stars
    
    @Column(name = "view_count")
    private Integer viewCount = 0;
    
    @Column(name = "is_anonymous", nullable = false)
    private Boolean isAnonymous = true;
    
    @Column(name = "created_by_user_id")
    private Long createdByUserId;
    
    @Column(name = "answered_by_user_id")
    private Long answeredByUserId;
    
    @Column(name = "answer_date")
    private LocalDateTime answerDate;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (dateAdded == null) {
            dateAdded = LocalDateTime.now();
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
