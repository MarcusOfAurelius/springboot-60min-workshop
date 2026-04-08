package com.bookstore.chapter4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Chapter 4: Transaction Management
 * 
 * Topics covered:
 * - @Transactional annotation
 * - Transaction propagation (REQUIRED, REQUIRES_NEW, etc.)
 * - Transaction isolation levels
 * - Rollback handling
 * - Optimistic vs Pessimistic locking
 * - Shopping cart and order processing
 */
@SpringBootApplication
@EnableTransactionManagement
public class Chapter4Application {

    public static void main(String[] args) {
        SpringApplication.run(Chapter4Application.class, args);
        System.out.println("\n🛒 Chapter 4 - Transactions is running!");
        System.out.println("📚 API: http://localhost:8083");
        System.out.println("🗄️  H2 Console: http://localhost:8083/h2-console");
        System.out.println("   JDBC URL: jdbc:h2:file:./data/bookstore-ch4\n");
    }
}
