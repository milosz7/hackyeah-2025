package com.example.Prompt2CodeDemo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FaqDto {
    
    private Long id;
    private String title;
    private String content;
    private String category;
    private List<String> labels;
    private LocalDateTime dateAdded;
    private String status;
    private String answer;
    private Integer rating;
    private Integer viewCount;
    private Boolean isAnonymous;
    private Long createdByUserId;
    private Long answeredByUserId;
    private LocalDateTime answerDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
