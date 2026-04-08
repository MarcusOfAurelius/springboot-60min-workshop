package com.bookstore.chapter3.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CreateAuthorRequest - DTO for creating a new author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAuthorRequest {
    
    private String name;
    private String biography;
    private String email;
    private String country;
}
