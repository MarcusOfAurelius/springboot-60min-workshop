a# 📚 Spring Boot Bookstore Workshop

Welcome to the **Spring Boot Bookstore Workshop**! This hands-on workshop will teach you Spring Boot fundamentals through building a complete bookstore application, one chapter at a time.

## 🎯 What You'll Learn

- **REST API Development** - Building RESTful endpoints
- **Database Integration** - Working with H2 database and JPA
- **OR Mapping** - Entity relationships and Hibernate
- **Request/Response Mapping** - DTOs and data transformation
- **Transaction Management** - Understanding @Transactional
- **Advanced Features** - Validation, exception handling, and more

## 📖 Workshop Chapters

### Chapter 1: Getting Started - Books Catalog 📕
**Concepts**: Basic Spring Boot setup, REST Controllers, In-memory data
- Create your first Spring Boot application
- Build a simple Books API (CRUD operations)
- Learn about `@RestController`, `@GetMapping`, `@PostMapping`
- Work with in-memory ArrayList

**Run**: `cd chapter-1-getting-started && mvn spring-boot:run`

### Chapter 2: Database & JPA - Persistent Bookstore 💾
**Concepts**: H2 Database, Spring Data JPA, Repositories
- Configure H2 database (file-based persistence)
- Create JPA entities with `@Entity`, `@Id`, `@GeneratedValue`
- Use Spring Data JPA repositories
- Learn about automatic CRUD operations

**Run**: `cd chapter-2-database-jpa && mvn spring-boot:run`

### Chapter 3: Advanced Mappings - Authors & Books 👥
**Concepts**: Entity Relationships, DTOs, Request/Response Mapping
- One-to-Many relationships (`Author` ↔ `Book`)
- Many-to-Many relationships (`Book` ↔ `Category`)
- DTO pattern for API responses
- MapStruct or manual mapping
- Learn `@OneToMany`, `@ManyToOne`, `@ManyToMany`

**Run**: `cd chapter-3-advanced-mappings && mvn spring-boot:run`

### Chapter 4: Transactions - Shopping Cart & Orders 🛒
**Concepts**: Transaction Management, Isolation Levels, Propagation
- Implement shopping cart functionality
- Create order processing with multiple operations
- Learn `@Transactional` annotation
- Understand transaction propagation and rollback
- Handle concurrent updates

**Run**: `cd chapter-4-transactions && mvn spring-boot:run`

### Chapter 5: Advanced Features - Reviews & Recommendations ⭐
**Concepts**: Validation, Exception Handling, Custom Queries
- Add book reviews and ratings
- Implement custom exception handling with `@ControllerAdvice`
- Bean validation with `@Valid`, `@NotNull`, `@Size`
- Custom JPQL queries
- Pagination and sorting

**Run**: `cd chapter-5-advanced-features && mvn spring-boot:run`

## 🚀 Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- Your favorite IDE (IntelliJ IDEA, Eclipse, VS Code)

### Build All Chapters
```bash
mvn clean install
```

### Run a Specific Chapter
```bash
cd chapter-X-name
mvn spring-boot:run
```

### Access H2 Console (Chapters 2-5)
- URL: `http://localhost:8080/h2-console`
- JDBC URL: Check each chapter's `application.properties`
- Username: `sa`
- Password: (empty)

## 🎓 Learning Path

1. **Start with Chapter 1** - Get comfortable with Spring Boot basics
2. **Progress sequentially** - Each chapter builds on previous concepts
3. **Experiment!** - Modify the code, break things, fix them
4. **Check the comments** - Code is heavily commented for learning
5. **Use the API** - Each chapter includes sample curl commands

## 🧪 Testing the APIs

Each chapter includes a `TESTING.md` file with curl commands and example requests. You can also use Postman, Insomnia, or your browser.

## 📚 Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [H2 Database](https://www.h2database.com/)

## 🎉 Have Fun Learning!

Remember: The best way to learn is by doing. Don't just read the code - run it, modify it, and make it your own!

Happy coding! 🚀
