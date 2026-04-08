package com.bookstore.chapter2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Chapter 2: Database Integration with H2 and JPA
 * 
 * New concepts in this chapter:
 * - H2 Database (in-memory and file-based)
 * - JPA Entities (@Entity, @Id, @GeneratedValue)
 * - Spring Data JPA Repositories
 * - Database migrations with data.sql
 * - H2 Console for database inspection
 */
@SpringBootApplication
public class Chapter2Application {

    public static void main(String[] args) {
        SpringApplication.run(Chapter2Application.class, args);
        System.out.println("\n💾 Chapter 2 - Database & JPA is running!");
        System.out.println("📚 API: http://localhost:8081/api/books");
        System.out.println("🗄️  H2 Console: http://localhost:8081/h2-console");
        System.out.println("   JDBC URL: jdbc:h2:file:./data/bookstore");
        System.out.println("   Username: sa");
        System.out.println("   Password: (leave empty)\n");
    }
}
