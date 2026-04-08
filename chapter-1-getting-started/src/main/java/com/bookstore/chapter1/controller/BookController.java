package com.bookstore.chapter1.controller;

import com.bookstore.chapter1.model.Book;
import com.bookstore.chapter1.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * BookController - REST API endpoints for managing books
 * 
 * @RestController = @Controller + @ResponseBody
 * This means all methods return data (JSON) instead of views (HTML)
 * 
 * @RequestMapping defines the base path for all endpoints in this controller
 * 
 * Learning REST conventions:
 * - GET    /books        -> List all books
 * - GET    /books/{id}   -> Get specific book
 * - POST   /books        -> Create new book
 * - PUT    /books/{id}   -> Update existing book
 * - DELETE /books/{id}   -> Delete book
 */
@RestController
@RequestMapping("/api/books")
public class BookController {
    
    // Dependency Injection - Spring automatically provides the BookService instance
    private final BookService bookService;
    
    /**
     * Constructor injection (recommended over field injection)
     * No need for @Autowired in modern Spring Boot!
     */
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    
    /**
     * GET /api/books - Get all books
     * 
     * Returns: List of all books in JSON format
     */
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }
    
    /**
     * GET /api/books/{id} - Get a specific book
     * 
     * @PathVariable extracts the {id} from the URL
     * ResponseEntity allows us to control HTTP status codes
     */
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id)
                .map(ResponseEntity::ok) // 200 OK if found
                .orElse(ResponseEntity.notFound().build()); // 404 if not found
    }
    
    /**
     * GET /api/books/search?title=Spring - Search books by title
     * 
     * @RequestParam extracts query parameters from the URL
     */
    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam String title) {
        return bookService.searchBooksByTitle(title);
    }
    
    /**
     * GET /api/books/genre/{genre} - Get books by genre
     */
    @GetMapping("/genre/{genre}")
    public List<Book> getBooksByGenre(@PathVariable String genre) {
        return bookService.getBooksByGenre(genre);
    }
    
    /**
     * POST /api/books - Create a new book
     * 
     * @RequestBody converts the JSON in the request body to a Book object
     * @ResponseStatus sets the HTTP status to 201 CREATED
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBook(@RequestBody Book book) {
        return bookService.createBook(book);
    }
    
    /**
     * PUT /api/books/{id} - Update an existing book
     * 
     * Combines @PathVariable and @RequestBody
     */
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        return bookService.updateBook(id, book)
                .map(ResponseEntity::ok) // 200 OK if updated
                .orElse(ResponseEntity.notFound().build()); // 404 if not found
    }
    
    /**
     * DELETE /api/books/{id} - Delete a book
     * 
     * Returns 204 NO CONTENT on success, 404 if book doesn't exist
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        boolean deleted = bookService.deleteBook(id);
        return deleted 
                ? ResponseEntity.noContent().build() // 204 NO CONTENT
                : ResponseEntity.notFound().build(); // 404 NOT FOUND
    }
    
    /**
     * GET / - Welcome endpoint (just for fun! 🎉)
     */
    @GetMapping("/")
    public String welcome() {
        return "🎉 Welcome to Chapter 1 - Bookstore API! Try GET /api/books to see all books!";
    }
}
