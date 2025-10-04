package com.example.Prompt2CodeDemo.config;

import com.example.Prompt2CodeDemo.dao.interfaces.FaqDao;
import com.example.Prompt2CodeDemo.entities.Faq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
public class FaqInitializerConfig implements CommandLineRunner {
    
    @Autowired
    private FaqDao faqDao;
    
    @Override
    public void run(String... args) throws Exception {
        initializeFaqData();
    }
    
    private void initializeFaqData() {
        // Check if FAQs already exist
        if (!faqDao.findAll().isEmpty()) {
            return;
        }
        
        // Sample FAQ data for testing
        List<Faq> sampleFaqs = Arrays.asList(
            createFaq("How to register as a supervised entity?", 
                "What are the requirements and process for registering as a supervised entity with UKNF?",
                "Registration", Arrays.asList("registration", "requirements", "process"), "PENDING", null, null, 0, true, 1L),
            
            createFaq("What documents are required for compliance?", 
                "What documentation needs to be submitted for regulatory compliance?",
                "Compliance", Arrays.asList("documents", "compliance", "regulatory"), "ANSWERED", 
                "You need to submit quarterly reports, annual financial statements, and any material changes to your business structure.", 4, 15, true, 1L, 2L),
            
            createFaq("How to submit reports online?", 
                "What is the process for submitting required reports through the online portal?",
                "Reporting", Arrays.asList("reports", "online", "submission"), "PUBLISHED", 
                "Log into the portal, navigate to the Reports section, fill out the required forms, and submit electronically.", 5, 25, true, 1L, 2L),
            
            createFaq("What are the penalties for non-compliance?", 
                "What penalties can be imposed for failing to meet regulatory requirements?",
                "Penalties", Arrays.asList("penalties", "non-compliance", "fines"), "ANSWERED", 
                "Penalties range from warnings to significant financial fines, depending on the severity and duration of non-compliance.", 3, 8, true, 1L, 2L),
            
            createFaq("How to update entity information?", 
                "How can I update my entity's registered information with UKNF?",
                "Updates", Arrays.asList("updates", "information", "changes"), "PENDING", null, null, 0, true, 1L),
            
            createFaq("What is the reporting schedule?", 
                "When are the different types of reports due throughout the year?",
                "Schedule", Arrays.asList("schedule", "deadlines", "timing"), "PUBLISHED", 
                "Quarterly reports are due 30 days after quarter end, annual reports by March 31st, and material changes must be reported within 10 business days.", 4, 18, true, 1L, 2L),
            
            createFaq("How to contact UKNF support?", 
                "What are the available channels for contacting UKNF support?",
                "Support", Arrays.asList("support", "contact", "help"), "ANSWERED", 
                "You can contact us via email at support@uknf.gov.pl, phone at +48 22 123 4567, or through the online portal messaging system.", 5, 12, true, 1L, 2L),
            
            createFaq("What are the data protection requirements?", 
                "What are the data protection and privacy requirements for supervised entities?",
                "Data Protection", Arrays.asList("data", "privacy", "protection"), "PENDING", null, null, 0, true, 1L),
            
            createFaq("How to access historical reports?", 
                "How can I access and download my previously submitted reports?",
                "Access", Arrays.asList("access", "historical", "download"), "PUBLISHED", 
                "Log into your account, go to the Reports section, and use the 'Historical Reports' tab to view and download past submissions.", 4, 6, true, 1L, 2L),
            
            createFaq("What are the audit requirements?", 
                "What are the requirements for external audits and internal controls?",
                "Audit", Arrays.asList("audit", "controls", "requirements"), "ANSWERED", 
                "Entities must maintain adequate internal controls and may be subject to external audits. Specific requirements depend on entity size and risk profile.", 3, 4, true, 1L, 2L)
        );
        
        // Save all sample FAQs
        for (Faq faq : sampleFaqs) {
            faqDao.save(faq);
        }
        
        System.out.println("FAQ data initialized with " + sampleFaqs.size() + " sample FAQs");
    }
    
    private Faq createFaq(String title, String content, String category, List<String> labels, 
                         String status, String answer, Integer rating, Integer viewCount, 
                         Boolean isAnonymous, Long createdByUserId, Long answeredByUserId) {
        Faq faq = new Faq();
        faq.setTitle(title);
        faq.setContent(content);
        faq.setCategory(category);
        faq.setLabels(labels);
        faq.setStatus(status);
        faq.setAnswer(answer);
        faq.setRating(rating);
        faq.setViewCount(viewCount);
        faq.setIsAnonymous(isAnonymous);
        faq.setCreatedByUserId(createdByUserId);
        faq.setAnsweredByUserId(answeredByUserId);
        faq.setDateAdded(LocalDateTime.now());
        
        if (answer != null) {
            faq.setAnswerDate(LocalDateTime.now());
        }
        
        return faq;
    }
    
    private Faq createFaq(String title, String content, String category, List<String> labels, 
                         String status, String answer, Integer rating, Integer viewCount, 
                         Boolean isAnonymous, Long createdByUserId) {
        return createFaq(title, content, category, labels, status, answer, rating, viewCount, 
                       isAnonymous, createdByUserId, null);
    }
}
