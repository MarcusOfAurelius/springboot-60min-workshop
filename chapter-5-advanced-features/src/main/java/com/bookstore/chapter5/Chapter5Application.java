package com.bookstore.chapter5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Chapter 5: Advanced Features
 * 
 * Topics covered:
 * - Bean Validation (@Valid, @NotNull, @Size, @Min, @Max, etc.)
 * - Global Exception Handling (@ControllerAdvice, @ExceptionHandler)
 * - Custom exceptions and error responses
 * - Pagination and sorting
 * - Custom JPQL queries
 * - Book reviews and ratings system
 */
@SpringBootApplication
public class Chapter5Application {

    public static void main(String[] args) {
        SpringApplication.run(Chapter5Application.class, args);
        System.out.println("\n⭐ Chapter 5 - Advanced Features is running!");
        System.out.println("📚 API: http://localhost:8084");
        System.out.println("🗄️  H2 Console: http://localhost:8084/h2-console");
        System.out.println("   JDBC URL: jdbc:h2:file:./data/bookstore-ch5\n");
    }
}
