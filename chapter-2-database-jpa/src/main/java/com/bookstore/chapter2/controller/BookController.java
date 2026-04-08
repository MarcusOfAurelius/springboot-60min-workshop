package com.bookstore.chapter2.controller;

import com.bookstore.chapter2.entity.Book;
import com.bookstore.chapter2.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * BookController - REST API endpoints
 * 
 * Same as Chapter 1, but now backed by a real database!
 * Data persists between restarts! 💾
 */
@RestController
@RequestMapping("/api/books")
public class BookController {
    
    private final BookService bookService;
    
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam String title) {
        return bookService.searchBooksByTitle(title);
    }
    
    @GetMapping("/genre/{genre}")
    public List<Book> getBooksByGenre(@PathVariable String genre) {
        return bookService.getBooksByGenre(genre);
    }
    
    @GetMapping("/author/{author}")
    public List<Book> getBooksByAuthor(@PathVariable String author) {
        return bookService.getBooksByAuthor(author);
    }
    
    /**
     * NEW: Get affordable books by author
     * Example: /api/books/author/Robert C. Martin/affordable?maxPrice=50.00
     */
    @GetMapping("/author/{author}/affordable")
    public List<Book> getAffordableBooksByAuthor(
            @PathVariable String author,
            @RequestParam Double maxPrice) {
        return bookService.getAffordableBooksByAuthor(author, maxPrice);
    }
    
    /**
     * NEW: Get books under a certain price
     * Example: /api/books/price/under?price=50.00
     */
    @GetMapping("/price/under")
    public List<Book> getBooksUnderPrice(@RequestParam Double price) {
        return bookService.getBooksUnderPrice(price);
    }

    /**
     * NEW: Get books under a certain price
     * Example: /api/books/price/under?price=50.00
     */
    @GetMapping("/price/greater")
    public List<Book> getBooksGreaterPrice(@RequestParam Double price) {
        return bookService.getBooksGreaterPrice(price);
    }

    
    /**
     * NEW: Get books in price range
     * Example: /api/books/price/range?min=30.00&max=60.00
     */
    @GetMapping("/price/range")
    public List<Book> getBooksInPriceRange(
            @RequestParam Double min,
            @RequestParam Double max) {
        return bookService.getBooksInPriceRange(min, max);
    }
    
    /**
     * NEW: Get low stock books
     * Example: /api/books/low-stock?threshold=10
     */
    @GetMapping("/low-stock")
    public List<Book> getLowStockBooks(@RequestParam(defaultValue = "5") Integer threshold) {
        return bookService.getLowStockBooks(threshold);
    }
    
    /**
     * NEW: Get statistics
     * Example: /api/books/stats
     */
    @GetMapping("/stats")
    public BookStats getStats() {
        long totalBooks = bookService.countBooks();
        return new BookStats(totalBooks);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBook(@RequestBody Book book) {
        return bookService.createBook(book);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        return bookService.updateBook(id, book)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        boolean deleted = bookService.deleteBook(id);
        return deleted 
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
    
    /**
     * Simple stats record for demonstration
     */
    private record BookStats(long totalBooks) {}
}
