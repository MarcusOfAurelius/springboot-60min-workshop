# 📚 Spring Boot Bookstore Workshop - Complete Guide

A comprehensive, hands-on workshop to master Spring Boot by building a complete bookstore application through 5 progressive chapters.

## 🎯 Workshop Overview

This workshop teaches Spring Boot fundamentals through practical, real-world examples. Each chapter builds on the previous one, introducing new concepts while maintaining a fun bookstore theme.

### **Who Is This For?**
- Java developers new to Spring Boot
- Developers wanting to understand Spring Boot internals
- Anyone looking for hands-on Spring Boot practice

### **Prerequisites**
- Java 17 or higher
- Maven 3.6+
- Your favorite IDE
- Basic understanding of Java and HTTP/REST

## 📖 Chapter Breakdown

### **Chapter 1: Getting Started - Books Catalog** 📕
**Port:** 8080 | **Difficulty:** ⭐  
**Time:** 30-45 minutes

**What You'll Build:**
- Simple REST API for book management
- In-memory storage using ArrayList
- CRUD operations

**Key Concepts:**
- `@SpringBootApplication` - The magic annotation
- `@RestController` - Building REST endpoints
- `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`
- Dependency injection with constructors
- `@Service` and component scanning

**Try It:**
```bash
cd chapter-1-getting-started
mvn spring-boot:run
curl http://localhost:8080/api/books
```

---

### **Chapter 2: Database & JPA - Persistent Bookstore** 💾
**Port:** 8081 | **Difficulty:** ⭐⭐  
**Time:** 1-1.5 hours

**What You'll Build:**
- H2 database integration (file-based)
- JPA entities with proper mapping
- Spring Data repositories
- Database initialization

**Key Concepts:**
- `@Entity`, `@Id`, `@GeneratedValue` - Entity mapping
- `@Column` constraints and configuration
- `JpaRepository<T, ID>` - Repository pattern
- Query methods (derived from method names)
- Custom JPQL queries with `@Query`
- H2 Console for database inspection

**Try It:**
```bash
cd chapter-2-database-jpa
mvn spring-boot:run
open http://localhost:8081/h2-console
```

**Database Details:**
- JDBC URL: `jdbc:h2:file:./data/bookstore`
- Username: `sa`
- Password: (empty)

---

### **Chapter 3: Advanced Mappings - Authors & Books** 👥
**Port:** 8082 | **Difficulty:** ⭐⭐⭐  
**Time:** 1.5-2 hours

**What You'll Build:**
- Multiple related entities (Authors, Books, Categories)
- One-to-Many and Many-to-Many relationships
- DTO pattern for API responses
- Entity-to-DTO mapping

**Key Concepts:**
- `@OneToMany` / `@ManyToOne` - One-to-Many relationships
- `@ManyToMany` with `@JoinTable` - Many-to-Many relationships
- Bidirectional relationships
- `CascadeType` and `orphanRemoval`
- `FetchType.LAZY` vs `FetchType.EAGER`
- Avoiding N+1 query problem with `JOIN FETCH`
- DTOs vs Entities
- `@JsonManagedReference` / `@JsonBackReference`

**Try It:**
```bash
cd chapter-3-advanced-mappings
mvn spring-boot:run
curl http://localhost:8082/api/authors/1
curl http://localhost:8082/api/books/category/Programming
```

---

### **Chapter 4: Transactions - Shopping Cart & Orders** 🛒
**Port:** 8083 | **Difficulty:** ⭐⭐⭐⭐  
**Time:** 1.5-2 hours

**What You'll Build:**
- Shopping cart functionality
- Order processing system
- Stock management with atomic operations
- Transaction rollback scenarios

**Key Concepts:**
- `@Transactional` annotation
- Transaction propagation (`REQUIRED`, `REQUIRES_NEW`, etc.)
- Transaction isolation levels
- Rollback strategies
- `@Version` for optimistic locking
- Atomic operations across entities
- Business transaction patterns

**Try It:**
```bash
cd chapter-4-transactions
mvn spring-boot:run

# Create an order
curl -X POST http://localhost:8083/api/orders \
  -H "Content-Type: application/json" \
  -d '{"customerName": "John", "customerEmail": "john@example.com", "bookQuantities": {"1": 2}}'

# Try to order too many (watch it rollback!)
curl -X POST http://localhost:8083/api/orders \
  -H "Content-Type: application/json" \
  -d '{"customerName": "Jane", "customerEmail": "jane@example.com", "bookQuantities": {"1": 100}}'
```

---

### **Chapter 5: Advanced Features - Reviews & Recommendations** ⭐
**Port:** 8084 | **Difficulty:** ⭐⭐⭐⭐⭐  
**Time:** 2-2.5 hours

**What You'll Build:**
- Book review and rating system
- Bean validation with custom messages
- Global exception handling
- Pagination and sorting
- Advanced search functionality

**Key Concepts:**
- Bean Validation (`@Valid`, `@NotNull`, `@Size`, `@Min`, `@Max`, `@Pattern`)
- `@ControllerAdvice` for global exception handling
- Custom exceptions (`ResourceNotFoundException`, `BadRequestException`)
- Consistent error responses
- `Pageable` interface for pagination
- `Sort` for sorting results
- Advanced JPQL queries
- Aggregating data (calculating averages)

**Try It:**
```bash
cd chapter-5-advanced-features
mvn spring-boot:run

# Get paginated books
curl "http://localhost:8084/api/books/page?page=0&size=5&sortBy=price&direction=desc"

# Add a review
curl -X POST http://localhost:8084/api/books/1/reviews \
  -H "Content-Type: application/json" \
  -d '{"reviewerName": "Alice", "rating": 5, "comment": "Amazing book!"}'

# Test validation error
curl -X POST http://localhost:8084/api/books \
  -H "Content-Type: application/json" \
  -d '{"title": "", "price": -10}'
```

---

## 🎯 Hands-On Workshop Exercises

### **1-Hour Practical Exercises** ⏱️

Each chapter includes a focused hands-on exercise designed for workshop participants with limited time. These exercises are specifically crafted to:

- ✅ Be completed in **10-12 minutes per chapter** (~1 hour total)
- ✅ Reinforce the **core concepts** of each chapter
- ✅ Require **actual code changes**, not just reading
- ✅ Build **incrementally** on previous knowledge

### **Exercise Overview**

| Chapter | Exercise | Time | Difficulty |
|---------|----------|------|------------|
| **Chapter 1** | Add a Book Count Endpoint | 10-12 min | ⭐ |
| **Chapter 2** | Find Books in Price Range | 10-12 min | ⭐⭐ |
| **Chapter 3** | Add Publication Year Field | 10-12 min | ⭐⭐⭐ |
| **Chapter 4** | Stock Reorder Threshold Check | 10-12 min | ⭐⭐⭐⭐ |
| **Chapter 5** | Custom ISBN Validator | 10-12 min | ⭐⭐⭐⭐⭐ |

**📘 Full Exercise Details:** See [WORKSHOP_EXERCISES.md](WORKSHOP_EXERCISES.md) for complete instructions, hints, and test commands.

**💡 Bonus Challenge:** If participants finish early, there's a cross-cutting "Featured Books" challenge that combines concepts from multiple chapters.

---

## 🚀 Getting Started

### **Quick Start (All Chapters)**

```bash
# Clone or navigate to the workshop directory
cd workshop-springboot

# Build all chapters
mvn clean install

# Run a specific chapter
cd chapter-1-getting-started
mvn spring-boot:run
```

### **Chapter-by-Chapter Approach** (Recommended)

1. Start with Chapter 1
2. Read the chapter README
3. Explore the code
4. Run the application
5. Test with the provided curl commands
6. Try the exercises
7. Move to next chapter

## 📊 Workshop Progress Tracker

Use this to track your learning:

- [ ] **Chapter 1** - Understand Spring Boot basics and REST controllers
- [ ] **Chapter 2** - Set up database and use JPA repositories
- [ ] **Chapter 3** - Master entity relationships and DTOs
- [ ] **Chapter 4** - Implement transactional operations
- [ ] **Chapter 5** - Add validation and exception handling

## 🎓 Learning Path

### **Beginner Path** (New to Spring Boot)
1. Read each chapter's README thoroughly
2. Run each application and observe the console output
3. Use H2 Console to inspect database changes
4. Test with provided curl commands
5. Modify code and see what happens
6. Complete the exercises

### **Intermediate Path** (Some Spring Boot experience)
1. Skim the README
2. Focus on new concepts in each chapter
3. Compare with your existing knowledge
4. Experiment with variations
5. Challenge yourself with exercises

### **Advanced Path** (Experienced developers)
1. Review the architectural decisions
2. Analyze the code patterns
3. Consider alternative implementations
4. Extend the functionality
5. Add features like caching, security, etc.

## 🛠️ Development Tips

### **Using H2 Console**
Each chapter (2-5) has H2 Console enabled:
- URL pattern: `http://localhost:PORT/h2-console`
- Use it to:
  - View table structures
  - Run SQL queries
  - Inspect data changes
  - Learn SQL generated by JPA

### **Testing with curl**
All chapters include curl examples. Tips:
- Use `-v` flag for verbose output
- Use `json_pp` or `jq` to format JSON responses
- Save common requests in shell scripts

### **IDE Setup**
Recommended plugins:
- **Lombok**: Required for `@Data`, `@AllArgsConstructor`, etc.
- **Spring Boot Tools**: Code completion and run configurations
- **HTTP Client**: Test endpoints without leaving IDE

### **Debugging**
Set `logging.level.org.hibernate.SQL=DEBUG` to see:
- All SQL queries generated
- Parameter bindings
- Query execution time

## 📝 Common Patterns Used

### **Dependency Injection**
```java
// Constructor injection (recommended)
public BookController(BookService bookService) {
    this.bookService = bookService;
}
```

### **Service Layer Pattern**
```
Controller → Service → Repository → Database
```

### **DTO Pattern**
```
Entity (database) → Service (business logic) → DTO (API) → Controller
```

### **Exception Handling**
```
Custom Exception → @ControllerAdvice → Consistent Error Response
```

## 🎯 After the Workshop

### **Next Steps**
1. **Add Spring Security**
   - User authentication
   - Role-based access control
   - JWT tokens

2. **Add Testing**
   - Unit tests with JUnit 5
   - Integration tests
   - MockMvc for controller tests

3. **Deploy Your App**
   - Docker containerization
   - Deploy to Heroku, AWS, or Azure
   - CI/CD pipeline

4. **Explore Microservices**
   - Spring Cloud
   - Service discovery
   - API Gateway

### **Recommended Resources**
- [Spring Boot Official Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA Guide](https://spring.io/guides/gs/accessing-data-jpa/)
- [Baeldung Spring Tutorials](https://www.baeldung.com/spring-boot)
- [Spring Boot GitHub](https://github.com/spring-projects/spring-boot)

## 🤝 Contributing

Found an issue or want to improve the workshop?
- Report bugs or suggest features
- Submit pull requests
- Share your completed projects

## 📄 License

This workshop is created for educational purposes. Feel free to use, modify, and share!

---

## 🎉 Congratulations!

By completing this workshop, you've learned:
- ✅ REST API development with Spring Boot
- ✅ Database persistence with JPA and H2
- ✅ Entity relationships and OR mapping
- ✅ Transaction management
- ✅ Validation and exception handling
- ✅ Pagination and advanced queries

You're now ready to build production-grade Spring Boot applications!

**Happy Coding! 🚀**

---

**Workshop Version:** 1.0.0  
**Last Updated:** March 2026  
**Built with:** Spring Boot 3.2.3, Java 17, H2 Database
