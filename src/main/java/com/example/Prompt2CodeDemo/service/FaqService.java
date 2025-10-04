package com.example.Prompt2CodeDemo.service;

import com.example.Prompt2CodeDemo.dto.*;
import com.example.Prompt2CodeDemo.entities.Faq;

import java.util.List;
import java.util.Optional;

public interface FaqService {
    
    List<FaqDto> getAllFaqs();
    
    Optional<FaqDto> getFaqById(Long id);
    
    FaqDto createFaq(FaqCreateRequest request, Long userId);
    
    Optional<FaqDto> updateFaq(Long id, FaqDto faqDto);
    
    boolean deleteFaq(Long id);
    
    List<FaqDto> searchFaqs(FaqSearchRequest searchRequest);
    
    List<FaqDto> getFaqsByCategory(String category);
    
    List<FaqDto> getFaqsByStatus(String status);
    
    List<FaqDto> getPublishedFaqs();
    
    List<FaqDto> getPendingFaqs();
    
    List<FaqDto> getAnsweredFaqs();
    
    FaqDto answerFaq(Long id, FaqAnswerRequest request, Long userId);
    
    FaqDto rateFaq(Long id, Integer rating);
    
    FaqDto viewFaq(Long id);
    
    List<String> getAllCategories();
    
    List<String> getAllLabels();
    
    List<FaqDto> getFaqsByLabels(List<String> labels);
}
