package com.bookstore.chapter2.service;

import com.bookstore.chapter2.entity.Book;
import com.bookstore.chapter2.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * BookService - Business Logic Layer
 * 
 * This is where we put our business logic.
 * The service layer sits between the controller (REST API) and repository (Database).
 * 
 * 🎯 Learning Points:
 * - Constructor Injection: Spring automatically injects BookRepository
 * - Repository Pattern: We use the repository to interact with the database
 * - Service methods typically handle business logic and coordinate between repositories
 */
@Service
public class BookService {
    
    private final BookRepository bookRepository;
    
    /**
     * Constructor Injection
     * Spring will automatically inject BookRepository when creating this bean
     */
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    
    /**
     * Get all books from the database
     */
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    
    /**
     * Get a book by ID
     */
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }
    
    /**
     * Search books by title (case-insensitive, partial match)
     */
    public List<Book> searchBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }
    
    /**
     * Get books by genre
     */
    public List<Book> getBooksByGenre(String genre) {
        return bookRepository.findByGenre(genre);
    }
    
    /**
     * Get books by author
     */
    public List<Book> getBooksByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }
    
    /**
     * Get affordable books by a specific author
     * Uses custom JPQL query
     */
    public List<Book> getAffordableBooksByAuthor(String author, Double maxPrice) {
        return bookRepository.findAffordableBooksByAuthor(author, maxPrice);
    }
    
    /**
     * Get books under a certain price
     */
    public List<Book> getBooksUnderPrice(Double price) {
        return bookRepository.findByPriceLessThan(price);
    }
    
    /**
     * Get books greater than a certain price
     */
    public List<Book> getBooksGreaterPrice(Double price) {
        return bookRepository.findByPriceGreaterThan(price);
    }
    
    /**
     * Get books in a price range
     */
    public List<Book> getBooksInPriceRange(Double min, Double max) {
        return bookRepository.findByPriceBetween(min, max);
    }
    
    /**
     * Get books with low stock
     * Uses custom native SQL query
     */
    public List<Book> getLowStockBooks(Integer threshold) {
        return bookRepository.findLowStockBooks(threshold);
    }
    
    /**
     * Create a new book
     * 
     * 💡 Note: save() works for both create and update!
     * - If ID is null -> INSERT
     * - If ID exists -> UPDATE
     */
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }
    
    /**
     * Update an existing book
     */
    public Optional<Book> updateBook(Long id, Book updatedBook) {
        return bookRepository.findById(id)
                .map(existingBook -> {
                    existingBook.setTitle(updatedBook.getTitle());
                    existingBook.setAuthor(updatedBook.getAuthor());
                    existingBook.setIsbn(updatedBook.getIsbn());
                    existingBook.setPrice(updatedBook.getPrice());
                    existingBook.setGenre(updatedBook.getGenre());
                    existingBook.setStock(updatedBook.getStock());
                    return bookRepository.save(existingBook);
                });
    }
    
    /**
     * Delete a book by ID
     * Returns true if deleted, false if book didn't exist
     */
    public boolean deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    /**
     * Count total books in database
     */
    public long countBooks() {
        return bookRepository.count();
    }
    
    /**
     * Check if a book exists by ISBN
     */
    public boolean existsByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn).isPresent();
    }
}
