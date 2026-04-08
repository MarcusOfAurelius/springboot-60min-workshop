package com.bookstore.chapter4.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Book Entity for Chapter 4
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
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false)
    private String author;
    
    @Column(nullable = false)
    private Double price;
    
    /**
     * @Version enables optimistic locking
     * Hibernate automatically checks if version changed when updating
     * Prevents lost updates in concurrent scenarios
     */
    @Version
    private Long version;
    
    @Column(nullable = false)
    private Integer stock;
    
    public Book(String title, String author, Double price, Integer stock) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.stock = stock;
    }
}
