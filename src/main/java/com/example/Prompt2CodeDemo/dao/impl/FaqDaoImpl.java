package com.example.Prompt2CodeDemo.dao.impl;

import com.example.Prompt2CodeDemo.dao.interfaces.FaqDao;
import com.example.Prompt2CodeDemo.dto.FaqSearchRequest;
import com.example.Prompt2CodeDemo.entities.Faq;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class FaqDaoImpl implements FaqDao {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public List<Faq> findAll() {
        String jpql = "SELECT f FROM Faq f ORDER BY f.dateAdded DESC";
        return entityManager.createQuery(jpql, Faq.class).getResultList();
    }
    
    @Override
    public Optional<Faq> findById(Long id) {
        Faq faq = entityManager.find(Faq.class, id);
        return Optional.ofNullable(faq);
    }
    
    @Override
    public Faq save(Faq faq) {
        if (faq.getId() == null) {
            entityManager.persist(faq);
        } else {
            faq = entityManager.merge(faq);
        }
        return faq;
    }
    
    @Override
    public void deleteById(Long id) {
        Faq faq = entityManager.find(Faq.class, id);
        if (faq != null) {
            entityManager.remove(faq);
        }
    }
    
    @Override
    public List<Faq> searchFaqs(FaqSearchRequest searchRequest) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Faq> query = cb.createQuery(Faq.class);
        Root<Faq> root = query.from(Faq.class);
        
        List<Predicate> predicates = new ArrayList<>();
        
        // Keyword search in title and content
        if (searchRequest.getKeyword() != null && !searchRequest.getKeyword().trim().isEmpty()) {
            String keyword = "%" + searchRequest.getKeyword().toLowerCase() + "%";
            Predicate titlePredicate = cb.like(cb.lower(root.get("title")), keyword);
            Predicate contentPredicate = cb.like(cb.lower(root.get("content")), keyword);
            predicates.add(cb.or(titlePredicate, contentPredicate));
        }
        
        // Title search
        if (searchRequest.getTitle() != null && !searchRequest.getTitle().trim().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("title")), 
                "%" + searchRequest.getTitle().toLowerCase() + "%"));
        }
        
        // Content search
        if (searchRequest.getContent() != null && !searchRequest.getContent().trim().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("content")), 
                "%" + searchRequest.getContent().toLowerCase() + "%"));
        }
        
        // Category filter
        if (searchRequest.getCategory() != null && !searchRequest.getCategory().trim().isEmpty()) {
            predicates.add(cb.equal(root.get("category"), searchRequest.getCategory()));
        }
        
        // Status filter
        if (searchRequest.getStatus() != null && !searchRequest.getStatus().trim().isEmpty()) {
            predicates.add(cb.equal(root.get("status"), searchRequest.getStatus()));
        }
        
        // Labels filter
        if (searchRequest.getLabels() != null && !searchRequest.getLabels().isEmpty()) {
            predicates.add(root.get("labels").in(searchRequest.getLabels()));
        }
        
        query.where(predicates.toArray(new Predicate[0]));
        
        // Sorting
        if (searchRequest.getSortBy() != null) {
            switch (searchRequest.getSortBy().toUpperCase()) {
                case "POPULARITY":
                    if ("DESC".equalsIgnoreCase(searchRequest.getSortOrder())) {
                        query.orderBy(cb.desc(root.get("viewCount")), cb.desc(root.get("dateAdded")));
                    } else {
                        query.orderBy(cb.asc(root.get("viewCount")), cb.desc(root.get("dateAdded")));
                    }
                    break;
                case "RATING":
                    if ("DESC".equalsIgnoreCase(searchRequest.getSortOrder())) {
                        query.orderBy(cb.desc(root.get("rating")), cb.desc(root.get("dateAdded")));
                    } else {
                        query.orderBy(cb.asc(root.get("rating")), cb.desc(root.get("dateAdded")));
                    }
                    break;
                case "DATE_ADDED":
                default:
                    if ("ASC".equalsIgnoreCase(searchRequest.getSortOrder())) {
                        query.orderBy(cb.asc(root.get("dateAdded")));
                    } else {
                        query.orderBy(cb.desc(root.get("dateAdded")));
                    }
                    break;
            }
        } else {
            query.orderBy(cb.desc(root.get("dateAdded")));
        }
        
        TypedQuery<Faq> typedQuery = entityManager.createQuery(query);
        
        // Pagination
        if (searchRequest.getPage() != null && searchRequest.getSize() != null) {
            int offset = searchRequest.getPage() * searchRequest.getSize();
            typedQuery.setFirstResult(offset);
            typedQuery.setMaxResults(searchRequest.getSize());
        }
        
        return typedQuery.getResultList();
    }
    
    @Override
    public List<Faq> findByCategory(String category) {
        String jpql = "SELECT f FROM Faq f WHERE f.category = :category ORDER BY f.dateAdded DESC";
        return entityManager.createQuery(jpql, Faq.class)
                .setParameter("category", category)
                .getResultList();
    }
    
    @Override
    public List<Faq> findByStatus(String status) {
        String jpql = "SELECT f FROM Faq f WHERE f.status = :status ORDER BY f.dateAdded DESC";
        return entityManager.createQuery(jpql, Faq.class)
                .setParameter("status", status)
                .getResultList();
    }
    
    @Override
    public List<Faq> findByLabels(List<String> labels) {
        String jpql = "SELECT f FROM Faq f WHERE f.labels IN :labels ORDER BY f.dateAdded DESC";
        return entityManager.createQuery(jpql, Faq.class)
                .setParameter("labels", labels)
                .getResultList();
    }
    
    @Override
    public List<Faq> findPublishedFaqs() {
        String jpql = "SELECT f FROM Faq f WHERE f.status = 'PUBLISHED' ORDER BY f.dateAdded DESC";
        return entityManager.createQuery(jpql, Faq.class).getResultList();
    }
    
    @Override
    public List<Faq> findPendingFaqs() {
        String jpql = "SELECT f FROM Faq f WHERE f.status = 'PENDING' ORDER BY f.dateAdded DESC";
        return entityManager.createQuery(jpql, Faq.class).getResultList();
    }
    
    @Override
    public List<Faq> findAnsweredFaqs() {
        String jpql = "SELECT f FROM Faq f WHERE f.status = 'ANSWERED' ORDER BY f.dateAdded DESC";
        return entityManager.createQuery(jpql, Faq.class).getResultList();
    }
    
    @Override
    public void incrementViewCount(Long id) {
        String jpql = "UPDATE Faq f SET f.viewCount = f.viewCount + 1 WHERE f.id = :id";
        entityManager.createQuery(jpql)
                .setParameter("id", id)
                .executeUpdate();
    }
    
    @Override
    public List<String> findAllCategories() {
        String jpql = "SELECT DISTINCT f.category FROM Faq f WHERE f.category IS NOT NULL ORDER BY f.category";
        return entityManager.createQuery(jpql, String.class).getResultList();
    }
    
    @Override
    public List<String> findAllLabels() {
        String jpql = "SELECT DISTINCT l FROM Faq f JOIN f.labels l ORDER BY l";
        return entityManager.createQuery(jpql, String.class).getResultList();
    }
}
