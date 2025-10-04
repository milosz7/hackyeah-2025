# FAQ Module Implementation

## Overview
This document describes the implementation of the FAQ (Frequently Asked Questions) module for the UKNF supervision system. The module provides a comprehensive Q&A system with anonymous question submission, answer management, and knowledge base functionality.

## Architecture

### Entity Layer
- **Faq.java**: Main entity with all required fields including title, content, category, labels, status, answer, rating, and audit fields
- Supports anonymous questions and tracks user interactions
- Includes view count tracking for popularity metrics

### DTO Layer
- **FaqDto.java**: Complete data transfer object for FAQ operations
- **FaqCreateRequest.java**: Request DTO for creating new FAQs (EXTERNAL users only)
- **FaqAnswerRequest.java**: Request DTO for answering FAQs (INTERNAL users only)
- **FaqSearchRequest.java**: Advanced search and filtering DTO

### Data Access Layer
- **FaqDao.java**: Interface defining all data access operations
- **FaqDaoImpl.java**: Implementation with advanced search, filtering, and sorting capabilities
- Supports complex queries with criteria API for dynamic filtering

### Service Layer
- **FaqService.java**: Business logic interface
- **FaqServiceImpl.java**: Implementation with transaction management and business rules
- Handles user permissions and data validation

### Controller Layer
- **FaqController.java**: REST API endpoints with proper security annotations
- Comprehensive endpoint coverage for all FAQ operations

## API Endpoints

### Public Endpoints (Knowledge Base)
```
GET    /api/faqs                    - Get all FAQs
GET    /api/faqs/{id}              - Get FAQ by ID
GET    /api/faqs/{id}/view         - View FAQ (increments view count)
GET    /api/faqs/published         - Get published FAQs
GET    /api/faqs/answered          - Get answered FAQs
GET    /api/faqs/category/{category} - Get FAQs by category
GET    /api/faqs/status/{status}    - Get FAQs by status
GET    /api/faqs/categories         - Get all categories
GET    /api/faqs/labels            - Get all labels
POST   /api/faqs/search            - Advanced search with filters
POST   /api/faqs/by-labels         - Get FAQs by labels
POST   /api/faqs/{id}/rate         - Rate FAQ (1-5 stars)
```

### EXTERNAL User Endpoints
```
POST   /api/faqs                   - Create new FAQ (anonymous)
```

### INTERNAL User Endpoints (UKNF_EMPLOYEE, UKNF_SYSTEM_ADMINISTRATOR)
```
GET    /api/faqs/pending          - Get pending FAQs
POST   /api/faqs/{id}/answer      - Answer FAQ
PUT    /api/faqs/{id}             - Update FAQ
DELETE /api/faqs/{id}             - Delete FAQ
```

## Security Configuration

The FAQ module implements role-based access control:

- **Public Access**: Knowledge base viewing, searching, and rating
- **EXTERNAL Users**: Can create anonymous questions
- **INTERNAL Users** (UKNF_EMPLOYEE, UKNF_SYSTEM_ADMINISTRATOR): Can manage questions and answers

## Features Implemented

### ✅ Question Management
- Anonymous question submission by EXTERNAL users
- Questions with title, content, category, labels, and date
- Status tracking (PENDING, ANSWERED, PUBLISHED, ARCHIVED)

### ✅ Answer Management
- Answer submission by UKNF employees
- Rating system (1-5 stars)
- Answer date tracking
- User attribution for answers

### ✅ Categorization
- Category-based organization
- Label/tag system for better filtering
- Dynamic category and label retrieval

### ✅ Search and Filtering
- Keyword search in title and content
- Category filtering
- Label filtering
- Status filtering
- Advanced search with multiple criteria

### ✅ Sorting Options
- By popularity (view count)
- By date added
- By rating
- Ascending/descending order

### ✅ Assessment System
- 1-5 star rating system
- View count tracking for popularity
- Rating aggregation

### ✅ Knowledge Base
- Public access to published FAQs
- Anonymized question display
- Searchable knowledge base

## Data Model

### FAQ Entity Fields
- `id`: Primary key
- `title`: Question title (max 500 chars)
- `content`: Question content
- `category`: FAQ category
- `labels`: List of tags/labels
- `dateAdded`: When question was submitted
- `status`: PENDING, ANSWERED, PUBLISHED, ARCHIVED
- `answer`: Answer content
- `rating`: 1-5 star rating
- `viewCount`: Popularity metric
- `isAnonymous`: Anonymous flag
- `createdByUserId`: Who created the question
- `answeredByUserId`: Who answered the question
- `answerDate`: When answered
- `createdAt`, `updatedAt`: Audit timestamps

## Sample Data

The system includes a data initializer that creates sample FAQs for testing:
- Registration questions
- Compliance queries
- Reporting procedures
- Support information
- Various categories and labels

## Usage Examples

### Create a Question (EXTERNAL User)
```json
POST /api/faqs
{
  "title": "How to submit reports?",
  "content": "What is the process for submitting quarterly reports?",
  "category": "Reporting",
  "labels": ["reports", "quarterly", "submission"]
}
```

### Answer a Question (INTERNAL User)
```json
POST /api/faqs/1/answer
{
  "answer": "Log into the portal and navigate to the Reports section...",
  "rating": 4
}
```

### Search FAQs
```json
POST /api/faqs/search
{
  "keyword": "reports",
  "category": "Reporting",
  "status": "ANSWERED",
  "sortBy": "POPULARITY",
  "sortOrder": "DESC",
  "page": 0,
  "size": 10
}
```

## Technical Implementation

### Dependencies
- Spring Boot 3.5+
- Spring Security for authorization
- MapStruct for entity-DTO mapping
- JPA/Hibernate for persistence
- Jakarta Validation for input validation

### Database Schema
- Main `faqs` table with all FAQ fields
- `faq_labels` collection table for many-to-many labels
- Proper indexing for search performance

### Performance Considerations
- Pagination support for large result sets
- Efficient search with criteria API
- View count optimization
- Proper database indexing

## Future Enhancements

1. **Advanced Analytics**: FAQ usage statistics and trends
2. **Auto-categorization**: ML-based category suggestion
3. **Related FAQs**: Recommendation system
4. **FAQ Templates**: Predefined question templates
5. **Bulk Operations**: Mass FAQ management
6. **Export Functionality**: FAQ data export
7. **Version History**: FAQ change tracking
8. **Multi-language Support**: Internationalization

## Testing

The implementation includes:
- Sample data initialization for testing
- Comprehensive error handling
- Input validation
- Security testing scenarios
- Performance testing considerations

## Deployment Notes

1. Ensure database tables are created with proper indexes
2. Configure security roles appropriately
3. Set up proper logging for FAQ operations
4. Consider caching for frequently accessed FAQs
5. Monitor performance for search operations

This FAQ module provides a complete, production-ready solution for managing questions and answers in the UKNF supervision system, with proper security, search capabilities, and user experience considerations.
