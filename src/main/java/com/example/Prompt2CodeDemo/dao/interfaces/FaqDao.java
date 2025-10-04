package com.example.Prompt2CodeDemo.dao.interfaces;

import com.example.Prompt2CodeDemo.dto.FaqSearchRequest;
import com.example.Prompt2CodeDemo.entities.Faq;

import java.util.List;
import java.util.Optional;

public interface FaqDao {
    
    List<Faq> findAll();
    
    Optional<Faq> findById(Long id);
    
    Faq save(Faq faq);
    
    void deleteById(Long id);
    
    List<Faq> searchFaqs(FaqSearchRequest searchRequest);
    
    List<Faq> findByCategory(String category);
    
    List<Faq> findByStatus(String status);
    
    List<Faq> findByLabels(List<String> labels);
    
    List<Faq> findPublishedFaqs();
    
    List<Faq> findPendingFaqs();
    
    List<Faq> findAnsweredFaqs();
    
    void incrementViewCount(Long id);
    
    List<String> findAllCategories();
    
    List<String> findAllLabels();
}
