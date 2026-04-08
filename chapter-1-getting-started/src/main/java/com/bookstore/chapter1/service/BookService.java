package com.bookstore.chapter1.service;

import com.bookstore.chapter1.model.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * BookService - Business logic for managing books
 * 
 * @Service annotation marks this as a Spring-managed service component.
 * Spring will create a singleton instance and manage its lifecycle.
 * 
 * In this chapter, we're using an in-memory ArrayList.
 * Chapter 2 will introduce database persistence! 💾
 */
@Service
public class BookService {
    
    // In-memory storage (data lost on restart! Chapter 2 will fix this)
    private final List<Book> books = new ArrayList<>();
    
    // ID generator (Chapter 2 will let the database handle this)
    private final AtomicLong idCounter = new AtomicLong(1);
    
    public BookService() {
        // Pre-populate with some fun books! 📚
        initializeSampleBooks();
    }
    
    /**
     * Get all books in the store
     */
    public List<Book> getAllBooks() {
        return new ArrayList<>(books); // Return a copy to prevent external modification
    }
    
    /**
     * Find a book by ID
     */
    public Optional<Book> getBookById(Long id) {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst();
    }
    
    /**
     * Search books by title (case-insensitive)
     * This is a simple implementation - Chapter 5 will have better search!
     */
    public List<Book> searchBooksByTitle(String title) {
        return books.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .toList();
    }
    
    /**
     * Add a new book to the store
     */
    public Book createBook(Book book) {
        book.setId(idCounter.getAndIncrement());
        books.add(book);
        return book;
    }
    
    /**
     * Update an existing book
     */
    public Optional<Book> updateBook(Long id, Book updatedBook) {
        Optional<Book> existingBook = getBookById(id);
        
        existingBook.ifPresent(book -> {
            book.setTitle(updatedBook.getTitle());
            book.setAuthor(updatedBook.getAuthor());
            book.setIsbn(updatedBook.getIsbn());
            book.setPrice(updatedBook.getPrice());
            book.setStock(updatedBook.getStock());
            book.setGenre(updatedBook.getGenre());
        });
        
        return existingBook;
    }
    
    /**
     * Delete a book
     */
    public boolean deleteBook(Long id) {
        return books.removeIf(book -> book.getId().equals(id));
    }
    
    /**
     * Get books by genre
     */
    public List<Book> getBooksByGenre(String genre) {
        return books.stream()
                .filter(book -> book.getGenre().equalsIgnoreCase(genre))
                .toList();
    }
    
    /**
     * Initialize the bookstore with some awesome books! 📚
     */
    private void initializeSampleBooks() {
        books.add(new Book(idCounter.getAndIncrement(), 
                "The Pragmatic Programmer", 
                "David Thomas, Andrew Hunt", 
                "978-0135957059", 
                49.99, 
                15, 
                "Programming"));
        
        books.add(new Book(idCounter.getAndIncrement(), 
                "Clean Code", 
                "Robert C. Martin", 
                "978-0132350884", 
                44.99, 
                20, 
                "Programming"));
        
        books.add(new Book(idCounter.getAndIncrement(), 
                "Spring in Action", 
                "Craig Walls", 
                "978-1617297571", 
                54.99, 
                10, 
                "Spring Framework"));
        
        books.add(new Book(idCounter.getAndIncrement(), 
                "Effective Java", 
                "Joshua Bloch", 
                "978-0134685991", 
                52.99, 
                12, 
                "Java"));
        
        books.add(new Book(idCounter.getAndIncrement(), 
                "Domain-Driven Design", 
                "Eric Evans", 
                "978-0321125217", 
                59.99, 
                8, 
                "Software Architecture"));
    }
}
