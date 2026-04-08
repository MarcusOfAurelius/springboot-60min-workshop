package com.bookstore.chapter1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Chapter 1: Getting Started with Spring Boot
 * 
 * Welcome to your first Spring Boot application! 📚
 * 
 * This annotation @SpringBootApplication does a lot of magic:
 * - @Configuration: Marks this as a configuration class
 * - @EnableAutoConfiguration: Tells Spring Boot to auto-configure based on dependencies
 * - @ComponentScan: Scans for components in this package and sub-packages
 */
@SpringBootApplication
public class Chapter1Application {

    public static void main(String[] args) {
        SpringApplication.run(Chapter1Application.class, args);
        System.out.println("\n🎉 Welcome to the Bookstore! Chapter 1 is running on http://localhost:8080\n");
    }
}
