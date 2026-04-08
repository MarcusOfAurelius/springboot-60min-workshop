# 📕 Chapter 1: Getting Started

Welcome to Chapter 1! In this chapter, you'll learn the fundamentals of Spring Boot by building a simple REST API for a bookstore.

## 🎯 Learning Objectives

By the end of this chapter, you will understand:
- How to create a Spring Boot application
- What `@SpringBootApplication` does
- REST Controller basics (`@RestController`, `@RequestMapping`)
- HTTP method mappings (`@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`)
- Dependency injection and service layer pattern
- Working with `@PathVariable` and `@RequestParam`
- Status codes and `ResponseEntity`

## 🏗️ Project Structure

```
chapter-1-getting-started/
├── src/main/java/com/bookstore/chapter1/
│   ├── Chapter1Application.java        # Main application entry point
│   ├── controller/
│   │   └── BookController.java         # REST API endpoints
│   ├── service/
│   │   └── BookService.java            # Business logic
│   └── model/
│       └── Book.java                   # Book data model
└── src/main/resources/
    ├── application.properties          # Configuration
    └── banner.txt                      # Custom startup banner
```

## 🚀 Running the Application

```bash
# From the chapter-1-getting-started directory
mvn spring-boot:run

# Or from the root directory
cd chapter-1-getting-started && mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## 🧪 Testing the API

### Get All Books
```bash
curl http://localhost:8080/api/books
```

### Get a Specific Book
```bash
curl http://localhost:8080/api/books/1
```

### Search Books by Title
```bash
curl "http://localhost:8080/api/books/search?title=Spring"
```

### Get Books by Genre
```bash
curl http://localhost:8080/api/books/genre/Programming
```

### Create a New Book
```bash
curl -X POST http://localhost:8080/api/books \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Java Concurrency in Practice",
    "author": "Brian Goetz",
    "isbn": "978-0321349606",
    "price": 49.99,
    "stock": 10,
    "genre": "Java"
  }'
```

### Update a Book
```bash
curl -X PUT http://localhost:8080/api/books/1 \
  -H "Content-Type: application/json" \
  -d '{
    "title": "The Pragmatic Programmer (Updated)",
    "author": "David Thomas, Andrew Hunt",
    "isbn": "978-0135957059",
    "price": 39.99,
    "stock": 20,
    "genre": "Programming"
  }'
```

### Delete a Book
```bash
curl -X DELETE http://localhost:8080/api/books/5
```

## 🔍 Key Concepts Explained

### @SpringBootApplication
The `@SpringBootApplication` annotation is a combination of:
- `@Configuration`: Marks the class as a source of bean definitions
- `@EnableAutoConfiguration`: Enables Spring Boot's auto-configuration
- `@ComponentScan`: Scans for components in the current package and sub-packages

### @RestController vs @Controller
- `@Controller`: Returns views (HTML pages)
- `@RestController`: Returns data (JSON/XML) - perfect for REST APIs

### Dependency Injection
Spring automatically creates and manages instances of your components. When `BookController` needs `BookService`, Spring injects it via constructor:

```java
public BookController(BookService bookService) {
    this.bookService = bookService;
}
```

### @Service vs @Component
- `@Service`: Used for business logic layer
- `@Component`: Generic stereotype for any Spring-managed component
- Both do the same thing, but `@Service` makes your code more readable

## 💡 What's Next?

In **Chapter 2**, we'll:
- Add a real H2 database
- Learn about JPA and Hibernate
- Replace ArrayList with database persistence
- Explore Spring Data repositories

## � Workshop Exercise

**Time allocation: 10-12 minutes**

### Exercise: Add a Book Count Endpoint

Implement a new endpoint that returns the total count of books for a specific genre.

**What you'll create:**
- Service method: `countBooksByGenre(String genre)`
- REST endpoint: `GET /api/books/genre/{genre}/count`

**Test command:**
```bash
curl http://localhost:8080/api/books/genre/Programming/count
```

**See full details in:** [WORKSHOP_EXERCISES.md](../WORKSHOP_EXERCISES.md#-chapter-1-getting-started---rest-api-basics)

## 🎓 Additional Practice Exercises

If you finish early, try these modifications:

1. **Add a description field** to the Book model
2. **Create an endpoint** to get books with stock less than a certain number
3. **Add price filtering** - get books under a certain price
4. **Implement a "bestsellers" endpoint** (you can hardcode some IDs)
5. **Add an author search endpoint**

## 🐛 Common Issues

### Port 8080 already in use?
Change the port in `application.properties`:
```properties
server.port=8081
```

### Lombok not working?
Make sure you have Lombok plugin installed in your IDE and annotation processing enabled.

## 📚 Additional Resources

- [Spring Boot Reference Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Building REST Services with Spring](https://spring.io/guides/tutorials/rest/)

Happy coding! 🚀
