package com.bookstore.chapter5.controller;

import com.bookstore.chapter5.entity.Book;
import com.bookstore.chapter5.entity.Review;
import com.bookstore.chapter5.service.BookService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * BookController with validation and exception handling
 */
@RestController
@RequestMapping("/api/books")
public class BookController {
    
    private final BookService bookService;
    
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    
    /**
     * Get all books (no pagination)
     */
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }
    
    /**
     * Get books with pagination and sorting
     * 
     * Example requests:
     * - GET /api/books/page?page=0&size=10
     * - GET /api/books/page?page=0&size=10&sort=title,asc
     * - GET /api/books/page?page=0&size=10&sort=price,desc
     */
    @GetMapping("/page")
    public Page<Book> getBooksPageable(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") 
                ? Sort.Direction.DESC 
                : Sort.Direction.ASC;
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        return bookService.getBooksPageable(pageable);
    }
    
    /**
     * Search books with pagination
     * 
     * Example: GET /api/books/search?keyword=spring&page=0&size=10
     */
    @GetMapping("/search")
    public Page<Book> searchBooks(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        return bookService.searchBooks(keyword, pageable);
    }
    
    /**
     * Get top-rated books
     * 
     * Example: GET /api/books/top-rated?minRating=4.0
     */
    @GetMapping("/top-rated")
    public List<Book> getTopRatedBooks(
            @RequestParam(defaultValue = "4.0") Double minRating) {
        return bookService.getTopRatedBooks(minRating);
    }
    
    /**
     * Get bestsellers
     * 
     * Example: GET /api/books/bestsellers?threshold=10
     */
    @GetMapping("/bestsellers")
    public List<Book> getBestsellers(
            @RequestParam(defaultValue = "10") Integer threshold) {
        return bookService.getBestsellers(threshold);
    }
    
    /**
     * Get book by ID
     * Throws ResourceNotFoundException if not found (handled by GlobalExceptionHandler)
     */
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }
    
    /**
     * Create a new book
     * 
     * @Valid triggers validation based on annotations in Book entity
     * If validation fails, MethodArgumentNotValidException is thrown
     * and handled by GlobalExceptionHandler
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBook(@Valid @RequestBody Book book) {
        return bookService.createBook(book);
    }
    
    /**
     * Update a book
     */
    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @Valid @RequestBody Book book) {
        return bookService.updateBook(id, book);
    }
    
    /**
     * Delete a book
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
    
    /**
     * Add a review to a book
     * 
     * POST /api/books/1/reviews
     */
    @PostMapping("/{bookId}/reviews")
    @ResponseStatus(HttpStatus.CREATED)
    public Review addReview(@PathVariable Long bookId, @Valid @RequestBody Review review) {
        return bookService.addReview(bookId, review);
    }
    
    /**
     * Get all reviews for a book
     * 
     * GET /api/books/1/reviews
     */
    @GetMapping("/{bookId}/reviews")
    public List<Review> getBookReviews(@PathVariable Long bookId) {
        return bookService.getBookReviews(bookId);
    }
}
