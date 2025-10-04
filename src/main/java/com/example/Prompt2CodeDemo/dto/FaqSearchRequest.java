package com.example.Prompt2CodeDemo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FaqSearchRequest {
    
    private String keyword;
    private String title;
    private String content;
    private String category;
    private List<String> labels;
    private String status;
    private String sortBy; // POPULARITY, DATE_ADDED, RATING
    private String sortOrder; // ASC, DESC
    private Integer page;
    private Integer size;
}
