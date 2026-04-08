package com.bookstore.chapter3.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Author Entity - represents a book author
 * 
 * Demonstrates @OneToMany relationship:
 * - One Author can write Many Books
 * - This is the "One" side of the relationship
 */
@Entity
@Table(name = "authors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(length = 1000)
    private String biography;
    
    private String email;
    
    private String country;
    
    /**
     * @OneToMany - One Author has Many Books
     * 
     * mappedBy = "author" - refers to the 'author' field in Book entity
     * cascade = ALL - operations on Author cascade to Books
     * orphanRemoval = true - if a book is removed from the list, it's deleted
     * 
     * @JsonManagedReference - prevents infinite recursion in JSON serialization
     * @ToString.Exclude - prevents infinite loop in toString()
     */
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @ToString.Exclude
    private List<Book> books = new ArrayList<>();
    
    /**
     * Helper method to add a book to this author
     * This maintains both sides of the bidirectional relationship
     */
    public void addBook(Book book) {
        books.add(book);
        book.setAuthor(this);
    }
    
    /**
     * Helper method to remove a book from this author
     */
    public void removeBook(Book book) {
        books.remove(book);
        book.setAuthor(null);
    }
    
    /**
     * Constructor without books list
     */
    public Author(String name, String biography, String email, String country) {
        this.name = name;
        this.biography = biography;
        this.email = email;
        this.country = country;
    }
}
