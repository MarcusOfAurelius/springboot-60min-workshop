package com.bookstore.chapter3.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * Category Entity - represents book categories/genres
 * 
 * Demonstrates @ManyToMany relationship:
 * - Many Books can belong to Many Categories
 * - This is the "owning" side (has @JoinTable)
 */
@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String name;
    
    @Column(length = 500)
    private String description;
    
    /**
     * @ManyToMany - Many Categories have Many Books
     * 
     * mappedBy = "categories" - this is the inverse side
     * Books entity owns the relationship
     */
    @ManyToMany(mappedBy = "categories")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Set<Book> books = new HashSet<>();
    
    /**
     * Constructor without books
     */
    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
