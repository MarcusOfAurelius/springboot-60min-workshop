package com.bookstore.chapter1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Book Model - represents a book in our bookstore
 * 
 * Using Lombok annotations to reduce boilerplate:
 * @Data - generates getters, setters, toString, equals, hashCode
 * @AllArgsConstructor - generates constructor with all fields
 * @NoArgsConstructor - generates no-args constructor (required by Spring)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private Double price;
    private Integer stock;
    private String genre;
    
    /**
     * Fun fact: In this chapter we're using simple POJOs.
     * In Chapter 2, we'll turn this into a JPA Entity! 🎯
     */
}
