package com.example.Prompt2CodeDemo.controllers;

import com.example.Prompt2CodeDemo.dto.*;
import com.example.Prompt2CodeDemo.service.FaqService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.example.Prompt2CodeDemo.service.UserService;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/faqs")
public class FaqController {
    
    @Autowired
    private FaqService faqService;

    @Autowired
    private UserService userService;
    // Get all FAQs (public - for knowledge base)
    @GetMapping
    public ResponseEntity<List<FaqDto>> getAllFaqs() {
        List<FaqDto> faqs = faqService.getAllFaqs();
        return ResponseEntity.ok(faqs);
    }
    
    // Get FAQ by ID (public - for knowledge base)
    @GetMapping("/{id}")
    public ResponseEntity<FaqDto> getFaqById(@PathVariable Long id) {
        Optional<FaqDto> faq = faqService.getFaqById(id);
        return faq.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }
    
    // View FAQ (increments view count)
    @GetMapping("/{id}/view")
    public ResponseEntity<FaqDto> viewFaq(@PathVariable Long id) {
        try {
            FaqDto faq = faqService.viewFaq(id);
            return ResponseEntity.ok(faq);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Create new FAQ (EXTERNAL users only)
    @PostMapping
    @PreAuthorize("hasRole('EXTERNAL')")
    public ResponseEntity<FaqDto> createFaq(@Valid @RequestBody FaqCreateRequest request) {
        // TODO: Get current user ID from security context
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername(); 
        Optional<UserDto> userDto = userService.getUserByEmail(username);
        Long userId = null;
        if (userDto.isPresent()) {
            userId = userDto.get().getId();
        } else {
            return ResponseEntity.notFound().build();
        }

        //Long userId = 1L; // Placeholder - should be extracted from security context
        FaqDto createdFaq = faqService.createFaq(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFaq);
    }
    
    // Search FAQs with filters and sorting
    @PostMapping("/search")
    public ResponseEntity<List<FaqDto>> searchFaqs(@Valid @RequestBody FaqSearchRequest searchRequest) {
        List<FaqDto> faqs = faqService.searchFaqs(searchRequest);
        return ResponseEntity.ok(faqs);
    }
    
    // Get FAQs by category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<FaqDto>> getFaqsByCategory(@PathVariable String category) {
        List<FaqDto> faqs = faqService.getFaqsByCategory(category);
        return ResponseEntity.ok(faqs);
    }
    
    // Get FAQs by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<FaqDto>> getFaqsByStatus(@PathVariable String status) {
        List<FaqDto> faqs = faqService.getFaqsByStatus(status);
        return ResponseEntity.ok(faqs);
    }
    
    // Get published FAQs (public knowledge base)
    @GetMapping("/published")
    public ResponseEntity<List<FaqDto>> getPublishedFaqs() {
        List<FaqDto> faqs = faqService.getPublishedFaqs();
        return ResponseEntity.ok(faqs);
    }
    
    // Get pending FAQs (INTERNAL users only)
    @GetMapping("/pending")
    @PreAuthorize("hasRole('UKNF_EMPLOYEE') or hasRole('UKNF_SYSTEM_ADMINISTRATOR')")
    public ResponseEntity<List<FaqDto>> getPendingFaqs() {
        List<FaqDto> faqs = faqService.getPendingFaqs();
        return ResponseEntity.ok(faqs);
    }
    
    // Get answered FAQs
    @GetMapping("/answered")
    public ResponseEntity<List<FaqDto>> getAnsweredFaqs() {
        List<FaqDto> faqs = faqService.getAnsweredFaqs();
        return ResponseEntity.ok(faqs);
    }
    
    // Answer FAQ (INTERNAL users only)
    @PostMapping("/{id}/answer")
    @PreAuthorize("hasRole('UKNF_EMPLOYEE') or hasRole('UKNF_SYSTEM_ADMINISTRATOR')")
    public ResponseEntity<FaqDto> answerFaq(@PathVariable Long id, @Valid @RequestBody FaqAnswerRequest request) {
        try {
            // TODO: Get current user ID from security context
            Long userId = 1L; // Placeholder - should be extracted from security context
            FaqDto answeredFaq = faqService.answerFaq(id, request, userId);
            return ResponseEntity.ok(answeredFaq);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Rate FAQ
    @PostMapping("/{id}/rate")
    public ResponseEntity<FaqDto> rateFaq(@PathVariable Long id, @RequestParam Integer rating) {
        try {
            FaqDto ratedFaq = faqService.rateFaq(id, rating);
            return ResponseEntity.ok(ratedFaq);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Update FAQ (INTERNAL users only)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('UKNF_EMPLOYEE') or hasRole('UKNF_SYSTEM_ADMINISTRATOR')")
    public ResponseEntity<FaqDto> updateFaq(@PathVariable Long id, @Valid @RequestBody FaqDto faqDto) {
        Optional<FaqDto> updatedFaq = faqService.updateFaq(id, faqDto);
        return updatedFaq.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
    }
    
    // Delete FAQ (INTERNAL users only)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('UKNF_EMPLOYEE') or hasRole('UKNF_SYSTEM_ADMINISTRATOR')")
    public ResponseEntity<Void> deleteFaq(@PathVariable Long id) {
        boolean deleted = faqService.deleteFaq(id);
        return deleted ? ResponseEntity.ok().build() 
                       : ResponseEntity.notFound().build();
    }
    
    // Get all categories
    @GetMapping("/categories")
    public ResponseEntity<List<String>> getAllCategories() {
        List<String> categories = faqService.getAllCategories();
        return ResponseEntity.ok(categories);
    }
    
    // Get all labels
    @GetMapping("/labels")
    public ResponseEntity<List<String>> getAllLabels() {
        List<String> labels = faqService.getAllLabels();
        return ResponseEntity.ok(labels);
    }
    
    // Get FAQs by labels
    @PostMapping("/by-labels")
    public ResponseEntity<List<FaqDto>> getFaqsByLabels(@RequestBody List<String> labels) {
        List<FaqDto> faqs = faqService.getFaqsByLabels(labels);
        return ResponseEntity.ok(faqs);
    }
}
