package com.bookstore.chapter3.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * AuthorDTO - Data Transfer Object for Author
 * 
 * Includes summary information about the author's books
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO {
    
    private Long id;
    private String name;
    private String biography;
    private String email;
    private String country;
    
    // Book summaries instead of full Book entities
    private List<BookSummaryDTO> books = new ArrayList<>();
    
    /**
     * Nested DTO for book summaries
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookSummaryDTO {
        private Long id;
        private String title;
        private String isbn;
        private Double price;
        private Integer stock;
    }
}
