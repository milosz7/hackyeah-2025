package com.example.Prompt2CodeDemo.service.impl;

import com.example.Prompt2CodeDemo.dao.interfaces.FaqDao;
import com.example.Prompt2CodeDemo.dto.*;
import com.example.Prompt2CodeDemo.entities.Faq;
import com.example.Prompt2CodeDemo.exception.ResourceNotFoundException;
import com.example.Prompt2CodeDemo.mapper.FaqMapper;
import com.example.Prompt2CodeDemo.service.FaqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FaqServiceImpl implements FaqService {
    
    @Autowired
    private FaqDao faqDao;
    
    @Autowired
    private FaqMapper faqMapper;
    
    @Override
    public List<FaqDto> getAllFaqs() {
        List<Faq> faqs = faqDao.findAll();
        return faqMapper.toDtoList(faqs);
    }
    
    @Override
    public Optional<FaqDto> getFaqById(Long id) {
        Optional<Faq> faq = faqDao.findById(id);
        return faq.map(faqMapper::toDto);
    }
    
    @Override
    public FaqDto createFaq(FaqCreateRequest request, Long userId) {
        Faq faq = new Faq();
        faq.setTitle(request.getTitle());
        faq.setContent(request.getContent());
        faq.setCategory(request.getCategory());
        faq.setLabels(request.getLabels());
        faq.setStatus("PENDING");
        faq.setIsAnonymous(true);
        faq.setCreatedByUserId(userId);
        faq.setViewCount(0);
        faq.setDateAdded(LocalDateTime.now());
        
        Faq savedFaq = faqDao.save(faq);
        return faqMapper.toDto(savedFaq);
    }
    
    @Override
    public Optional<FaqDto> updateFaq(Long id, FaqDto faqDto) {
        Optional<Faq> existingFaq = faqDao.findById(id);
        if (existingFaq.isPresent()) {
            Faq faq = existingFaq.get();
            faq.setTitle(faqDto.getTitle());
            faq.setContent(faqDto.getContent());
            faq.setCategory(faqDto.getCategory());
            faq.setLabels(faqDto.getLabels());
            faq.setStatus(faqDto.getStatus());
            faq.setAnswer(faqDto.getAnswer());
            faq.setRating(faqDto.getRating());
            
            Faq updatedFaq = faqDao.save(faq);
            return Optional.of(faqMapper.toDto(updatedFaq));
        }
        return Optional.empty();
    }
    
    @Override
    public boolean deleteFaq(Long id) {
        Optional<Faq> faq = faqDao.findById(id);
        if (faq.isPresent()) {
            faqDao.deleteById(id);
            return true;
        }
        return false;
    }
    
    @Override
    public List<FaqDto> searchFaqs(FaqSearchRequest searchRequest) {
        List<Faq> faqs = faqDao.searchFaqs(searchRequest);
        return faqMapper.toDtoList(faqs);
    }
    
    @Override
    public List<FaqDto> getFaqsByCategory(String category) {
        List<Faq> faqs = faqDao.findByCategory(category);
        return faqMapper.toDtoList(faqs);
    }
    
    @Override
    public List<FaqDto> getFaqsByStatus(String status) {
        List<Faq> faqs = faqDao.findByStatus(status);
        return faqMapper.toDtoList(faqs);
    }
    
    @Override
    public List<FaqDto> getPublishedFaqs() {
        List<Faq> faqs = faqDao.findPublishedFaqs();
        return faqMapper.toDtoList(faqs);
    }
    
    @Override
    public List<FaqDto> getPendingFaqs() {
        List<Faq> faqs = faqDao.findPendingFaqs();
        return faqMapper.toDtoList(faqs);
    }
    
    @Override
    public List<FaqDto> getAnsweredFaqs() {
        List<Faq> faqs = faqDao.findAnsweredFaqs();
        return faqMapper.toDtoList(faqs);
    }
    
    @Override
    public FaqDto answerFaq(Long id, FaqAnswerRequest request, Long userId) {
        Optional<Faq> faqOpt = faqDao.findById(id);
        if (faqOpt.isEmpty()) {
            throw new ResourceNotFoundException("FAQ not found with id: " + id);
        }
        
        Faq faq = faqOpt.get();
        faq.setAnswer(request.getAnswer());
        faq.setRating(request.getRating());
        faq.setStatus("ANSWERED");
        faq.setAnsweredByUserId(userId);
        faq.setAnswerDate(LocalDateTime.now());
        
        Faq savedFaq = faqDao.save(faq);
        return faqMapper.toDto(savedFaq);
    }
    
    @Override
    public FaqDto rateFaq(Long id, Integer rating) {
        Optional<Faq> faqOpt = faqDao.findById(id);
        if (faqOpt.isEmpty()) {
            throw new ResourceNotFoundException("FAQ not found with id: " + id);
        }
        
        Faq faq = faqOpt.get();
        faq.setRating(rating);
        
        Faq savedFaq = faqDao.save(faq);
        return faqMapper.toDto(savedFaq);
    }
    
    @Override
    public FaqDto viewFaq(Long id) {
        Optional<Faq> faqOpt = faqDao.findById(id);
        if (faqOpt.isEmpty()) {
            throw new ResourceNotFoundException("FAQ not found with id: " + id);
        }
        
        // Increment view count
        faqDao.incrementViewCount(id);
        
        // Return the updated FAQ
        Optional<Faq> updatedFaq = faqDao.findById(id);
        return faqMapper.toDto(updatedFaq.get());
    }
    
    @Override
    public List<String> getAllCategories() {
        return faqDao.findAllCategories();
    }
    
    @Override
    public List<String> getAllLabels() {
        return faqDao.findAllLabels();
    }
    
    @Override
    public List<FaqDto> getFaqsByLabels(List<String> labels) {
        List<Faq> faqs = faqDao.findByLabels(labels);
        return faqMapper.toDtoList(faqs);
    }
}
