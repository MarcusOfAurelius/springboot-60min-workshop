package com.bookstore.chapter5.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * Book Entity with validation annotations
 * 
 * Bean Validation annotations:
 * - @NotNull, @NotBlank, @NotEmpty
 * - @Size, @Min, @Max
 * - @Email, @Pattern
 * - @Positive, @PositiveOrZero
 */
@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters")
    @Column(nullable = false)
    private String title;
    
    @NotBlank(message = "Author is required")
    @Column(nullable = false)
    private String author;
    
    @Pattern(regexp = "^(97(8|9))?\\d{9}(\\d|X)$", message = "Invalid ISBN format")
    @Column(unique = true)
    private String isbn;
    
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    @DecimalMin(value = "0.01", message = "Price must be at least 0.01")
    @DecimalMax(value = "9999.99", message = "Price must not exceed 9999.99")
    @Column(nullable = false)
    private Double price;
    
    @NotNull(message = "Stock is required")
    @PositiveOrZero(message = "Stock cannot be negative")
    @Column(nullable = false)
    private Integer stock;
    
    @Size(max = 2000, message = "Description must not exceed 2000 characters")
    @Column(length = 2000)
    private String description;
    
    /**
     * Reviews for this book
     */
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @ToString.Exclude
    private List<Review> reviews = new ArrayList<>();
    
    /**
     * Average rating (calculated from reviews)
     */
    @Column
    private Double averageRating;
    
    /**
     * Helper method to add a review
     */
    public void addReview(Review review) {
        reviews.add(review);
        review.setBook(this);
        updateAverageRating();
    }
    
    /**
     * Calculate average rating from all reviews
     */
    public void updateAverageRating() {
        if (reviews.isEmpty()) {
            this.averageRating = null;
        } else {
            this.averageRating = reviews.stream()
                    .mapToInt(Review::getRating)
                    .average()
                    .orElse(0.0);
        }
    }
    
    public Book(String title, String author, String isbn, Double price, Integer stock, String description) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.price = price;
        this.stock = stock;
        this.description = description;
    }
}
