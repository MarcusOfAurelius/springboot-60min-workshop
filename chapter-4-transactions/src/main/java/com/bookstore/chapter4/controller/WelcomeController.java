package com.bookstore.chapter4.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * WelcomeController - Root endpoint for the application
 * 
 * This controller handles the root path (/) to provide a welcome message
 * when accessing http://localhost:8080
 */
@RestController
public class WelcomeController {
    
    /**
     * GET / - Welcome endpoint
     * 
     * Returns a friendly welcome message with information about the API
     */
    @GetMapping("/")
    public String welcome() {
        return "🎉 Welcome to Chapter 4: Transaction Management - Bookstore API! Try GET /api/books to see all books!";
    }
}
