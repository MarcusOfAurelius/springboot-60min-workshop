package com.bookstore.chapter3.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * Book Entity - represents a book in the bookstore
 * 
 * Demonstrates multiple relationship types:
 * - @ManyToOne with Author (many books to one author)
 * - @ManyToMany with Categories (many books to many categories)
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
    
    @Column(unique = true)
    private String isbn;
    
    @Column(nullable = false)
    private Double price;
    
    @Column(nullable = false)
    private Integer stock;
    
    @Column(length = 2000)
    private String description;
    
    /**
     * @ManyToOne - Many Books belong to One Author
     * 
     * This is the "Many" side of the relationship
     * 
     * fetch = LAZY - author is loaded only when accessed (prevents N+1 queries)
     * @JoinColumn - specifies the foreign key column name
     * @JsonBackReference - prevents infinite recursion (paired with @JsonManagedReference)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    @JsonBackReference
    @EqualsAndHashCode.Exclude
    private Author author;
    
    /**
     * @ManyToMany - Many Books belong to Many Categories
     * 
     * @JoinTable defines the join table:
     * - name: table name
     * - joinColumns: FK to this entity (Book)
     * - inverseJoinColumns: FK to the other entity (Category)
     */
    @ManyToMany
    @JoinTable(
        name = "book_categories",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @EqualsAndHashCode.Exclude
    private Set<Category> categories = new HashSet<>();
    
    /**
     * Helper method to add a category
     */
    public void addCategory(Category category) {
        this.categories.add(category);
        category.getBooks().add(this);
    }
    
    /**
     * Helper method to remove a category
     */
    public void removeCategory(Category category) {
        this.categories.remove(category);
        category.getBooks().remove(this);
    }
    
    /**
     * Constructor for creating a book (without relationships)
     */
    public Book(String title, String isbn, Double price, Integer stock, String description) {
        this.title = title;
        this.isbn = isbn;
        this.price = price;
        this.stock = stock;
        this.description = description;
    }
}
