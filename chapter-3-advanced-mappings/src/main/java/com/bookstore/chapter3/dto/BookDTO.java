package com.bookstore.chapter3.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * BookDTO - Data Transfer Object for Book
 * 
 * DTOs are used to:
 * 1. Control what data is exposed in API responses
 * 2. Avoid circular references in JSON serialization
 * 3. Decouple API structure from database structure
 * 4. Include data from multiple entities
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    
    private Long id;
    private String title;
    private String isbn;
    private Double price;
    private Integer stock;
    private String description;
    
    // Instead of the full Author entity, just include needed fields
    private String authorName;
    private Long authorId;
    
    // Category names instead of full Category entities
    private Set<String> categories = new HashSet<>();
}
