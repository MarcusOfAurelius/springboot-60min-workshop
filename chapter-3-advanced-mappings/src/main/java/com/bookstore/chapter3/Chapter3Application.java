package com.bookstore.chapter3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Chapter 3: Advanced Mappings and Relationships
 * 
 * New concepts in this chapter:
 * - Entity relationships (@OneToMany, @ManyToOne, @ManyToMany)
 * - Bidirectional relationships
 * - Cascade types and orphan removal
 * - Fetch types (LAZY vs EAGER)
 * - DTOs (Data Transfer Objects)
 * - Request/Response mapping
 * - Avoiding N+1 query problems
 */
@SpringBootApplication
public class Chapter3Application {

    public static void main(String[] args) {
        SpringApplication.run(Chapter3Application.class, args);
        System.out.println("\n👥 Chapter 3 - Advanced Mappings is running!");
        System.out.println("📚 API: http://localhost:8082");
        System.out.println("🗄️  H2 Console: http://localhost:8082/h2-console");
        System.out.println("   JDBC URL: jdbc:h2:file:./data/bookstore-ch3");
        System.out.println("   Username: sa");
        System.out.println("   Password: (leave empty)\n");
    }
}
