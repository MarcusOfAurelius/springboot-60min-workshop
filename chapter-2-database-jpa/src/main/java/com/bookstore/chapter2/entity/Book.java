package com.bookstore.chapter2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Book Entity - JPA Entity representing a book in the database
 * 
 * New JPA annotations:
 * @Entity - marks this class as a JPA entity (maps to a database table)
 * @Table - specifies the table name (optional, defaults to class name)
 * @Id - marks the primary key field
 * @GeneratedValue - auto-generates the ID value
 * @Column - configures column properties
 */
@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    
    /**
     * Primary Key with Auto-Increment
     * IDENTITY strategy: database handles ID generation
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Column constraints:
     * - nullable = false means NOT NULL in database
     * - length = 255 sets VARCHAR size
     */
    @Column(nullable = false, length = 255)
    private String title;
    
    @Column(nullable = false, length = 255)
    private String author;
    
    /**
     * unique = true creates a UNIQUE constraint
     * ISBN should be unique for each book
     */
    @Column(unique = true, length = 20)
    private String isbn;
    
    @Column(nullable = false)
    private Double price;
    
    @Column(nullable = false)
    private Integer stock;
    
    @Column(length = 100)
    private String genre;
    
    /**
     * Helper constructor (without ID - database will generate it)
     */
    public Book(String title, String author, String isbn, Double price, Integer stock, String genre) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.price = price;
        this.stock = stock;
        this.genre = genre;
    }
}
