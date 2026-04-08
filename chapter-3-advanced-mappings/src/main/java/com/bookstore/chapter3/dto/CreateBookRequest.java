package com.bookstore.chapter3.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * CreateBookRequest - DTOut for creating a new book
 * 
 * Separates the request format from the entity structure
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookRequest {
    
    private String title;
    private String isbn;
    private Double price;
    private Integer stock;
    private String description;
    
    // Reference author by ID instead of embedding full author
    private Long authorId;
    
    // Reference categories by their IDs
    private Set<Long> categoryIds;
}
