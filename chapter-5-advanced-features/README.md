# ⭐ Chapter 5: Advanced Features

Master advanced Spring Boot features with validation, exception handling, pagination, and more!

## 🎯 Learning Objectives

- Bean Validation (`@Valid`, `@NotNull`, `@Size`, `@Min`, `@Max`)
- Global exception handling (`@ControllerAdvice`, `@ExceptionHandler`)
- Custom exceptions and error responses
- Pagination and sorting with Spring Data
- Advanced JPQL queries
- Building a review system with ratings

## 🚀 Running

```bash
cd chapter-5-advanced-features
mvn spring-boot:run
```

Access: http://localhost:8084

## 🧪 Testing the API

### Validation Examples

```bash
# Valid book creation
curl -X POST http://localhost:8084/api/books \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Design Patterns",
    "author": "Gang of Four",
    "isbn": "9780201633610",
    "price": 47.99,
    "stock": 10,
    "description": "Elements of Reusable Object-Oriented Software"
  }'

# Invalid book - triggers validation errors
curl -X POST http://localhost:8084/api/books \
  -H "Content-Type: application/json" \
  -d '{
    "title": "",
    "author": "Test",
    "price": -10,
    "stock": -5
  }'
```

### Pagination & Sorting

```bash
# Get first page (10 items), sorted by title
curl "http://localhost:8084/api/books/page?page=0&size=10&sortBy=title&direction=asc"

# Get second page, sorted by price descending
curl "http://localhost:8084/api/books/page?page=1&size=5&sortBy=price&direction=desc"

# Search with pagination
curl "http://localhost:8084/api/books/search?keyword=Spring&page=0&size=10"
```

### Advanced Queries

```bash
# Get top-rated books (rating >= 4.5)
curl "http://localhost:8084/api/books/top-rated?minRating=4.5"

# Get bestsellers (low stock items)
curl "http://localhost:8084/api/books/bestsellers?threshold=15"
```

### Reviews

```bash
# Add a review to a book
curl -X POST http://localhost:8084/api/books/1/reviews \
  -H "Content-Type: application/json" \
  -d '{
    "reviewerName": "John Reader",
    "rating": 5,
    "comment": "Outstanding book! Highly recommended for all developers."
  }'

# Get all reviews for a book
curl http://localhost:8084/api/books/1/reviews

# Invalid review - validation error
curl -X POST http://localhost:8084/api/books/1/reviews \
  -H "Content-Type: application/json" \
  -d '{
    "reviewerName": "X",
    "rating": 10,
    "comment": "Too short"
  }'
```

### Exception Handling

```bash
# Resource not found (404)
curl http://localhost:8084/api/books/999

# Validation error (400)
curl -X POST http://localhost:8084/api/books \
  -H "Content-Type: application/json" \
  -d '{"title": ""}'
```

## 🔍 Key Concepts

### Bean Validation Annotations

```java
@NotBlank(message = "Title is required")
@Size(min = 1, max = 255)
private String title;

@Positive(message = "Price must be positive")
@DecimalMin(value = "0.01")
@DecimalMax(value = "9999.99")
private Double price;

@Pattern(regexp = "^(97(8|9))?\\d{9}(\\d|X)$")
private String isbn;
```

### Global Exception Handler

```java
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(
        ResourceNotFoundException ex) {
        // Return consistent error response
    }
}
```

### Pagination with Spring Data

```java
Pageable pageable = PageRequest.of(page, size, Sort.by("title"));
Page<Book> books = repository.findAll(pageable);
```

## 📊 Response Examples

### Successful Response
```json
{
  "id": 1,
  "title": "Clean Code",
  "author": "Robert C. Martin",
  "price": 44.99,
  "averageRating": 4.7
}
```

### Validation Error Response
```json
{
  "status": 400,
  "error": "Validation Failed",
  "message": "{title=Title is required, price=Price must be positive}",
  "timestamp": "2026-03-29T10:30:00"
}
```

### Not Found Error Response
```json
{
  "status": 404,
  "error": "Resource Not Found",
  "message": "Book not found with id: '999'",
  "timestamp": "2026-03-29T10:30:00"
}
```

## � Workshop Exercise

**Time allocation: 10-12 minutes**

### Exercise: Create a Custom ISBN Validator

Implement a custom validation annotation for ISBN format validation.

**What you'll create:**
- Custom annotation: `@ValidISBN`
- Validator class: `ISBNValidator` implementing `ConstraintValidator`
- Validation logic: Check ISBN starts with 978 or 979 and has correct format
- Apply the annotation to book creation requests

**Test commands:**
```bash
# Valid ISBN
curl -X POST http://localhost:8084/api/books \
  -H "Content-Type: application/json" \
  -d '{"title":"Test","author":"Author","isbn":"978-0134685991","price":39.99,"stock":10}'

# Invalid ISBN (should fail validation)
curl -X POST http://localhost:8084/api/books \
  -H "Content-Type: application/json" \
  -d '{"title":"Test","author":"Author","isbn":"123-4567890123","price":39.99,"stock":10}'
```

**See full details in:** [WORKSHOP_EXERCISES.md](../WORKSHOP_EXERCISES.md#-chapter-5-advanced-features---custom-validation)

## �🎓 Complete Workshop!

🎉 **Congratulations!** You've completed all 5 chapters!

You've learned:
- ✅ REST API basics
- ✅ Database integration with JPA
- ✅ Entity relationships and mappings
- ✅ Transaction management
- ✅ Validation and exception handling
- ✅ Pagination and advanced queries

## 🚀 Next Steps

1. **Build your own project** using these concepts
2. **Explore Spring Security** for authentication
3. **Learn about Spring Cloud** for microservices
4. **Add testing** with JUnit and Mockito
5. **Deploy** to the cloud (AWS, Azure, Heroku)

Happy coding! 🚀
