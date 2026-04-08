package com.bookstore.chapter3.service;

import com.bookstore.chapter3.dto.BookDTO;
import com.bookstore.chapter3.dto.CreateBookRequest;
import com.bookstore.chapter3.entity.Author;
import com.bookstore.chapter3.entity.Book;
import com.bookstore.chapter3.entity.Category;
import com.bookstore.chapter3.mapper.BookMapper;
import com.bookstore.chapter3.repository.AuthorRepository;
import com.bookstore.chapter3.repository.BookRepository;
import com.bookstore.chapter3.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * BookService - handles book-related business logic
 * 
 * @Transactional annotations:
 * - readOnly = true: optimizes read operations
 * - @Transactional on write methods: ensures atomicity
 */
@Service
@Transactional(readOnly = true)
public class BookService {
    
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final BookMapper bookMapper;
    
    public BookService(BookRepository bookRepository, 
                      AuthorRepository authorRepository, 
                      CategoryRepository categoryRepository, 
                      BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.bookMapper = bookMapper;
    }
    
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public Optional<BookDTO> getBookById(Long id) {
        // Use JOIN FETCH to avoid N+1 query problem
        return bookRepository.findByIdWithDetails(id)
                .map(bookMapper::toDTO);
    }
    
    public List<BookDTO> searchBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public List<BookDTO> getBooksByAuthor(Long authorId) {
        return bookRepository.findByAuthorId(authorId).stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public List<BookDTO> getBooksByCategory(String categoryName) {
        return bookRepository.findByCategoryName(categoryName).stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Create a new book with author and categories
     * 
     * This demonstrates:
     * - Using @Transactional for write operations
     * - Working with entity relationships
     * - Error handling for missing references
     */
    @Transactional
    public BookDTO createBook(CreateBookRequest request) {
        // Find the author
        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + request.getAuthorId()));
        
        // Create the book
        Book book = new Book(
            request.getTitle(),
            request.getIsbn(),
            request.getPrice(),
            request.getStock(),
            request.getDescription()
        );
        
        // Set the author (Many-to-One relationship)
        book.setAuthor(author);
        
        // Find and add categories (Many-to-Many relationship)
        if (request.getCategoryIds() != null && !request.getCategoryIds().isEmpty()) {
            Set<Category> categories = request.getCategoryIds().stream()
                    .map(categoryId -> categoryRepository.findById(categoryId)
                            .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId)))
                    .collect(Collectors.toSet());
            book.setCategories(categories);
        }
        
        Book saved = bookRepository.save(book);
        return bookMapper.toDTO(saved);
    }
    
    @Transactional
    public Optional<BookDTO> updateBook(Long id, CreateBookRequest request) {
        return bookRepository.findById(id)
                .map(book -> {
                    book.setTitle(request.getTitle());
                    book.setIsbn(request.getIsbn());
                    book.setPrice(request.getPrice());
                    book.setStock(request.getStock());
                    book.setDescription(request.getDescription());
                    
                    // Update author if changed
                    if (request.getAuthorId() != null) {
                        Author author = authorRepository.findById(request.getAuthorId())
                                .orElseThrow(() -> new RuntimeException("Author not found"));
                        book.setAuthor(author);
                    }
                    
                    // Update categories if provided
                    if (request.getCategoryIds() != null) {
                        Set<Category> categories = request.getCategoryIds().stream()
                                .map(categoryId -> categoryRepository.findById(categoryId)
                                        .orElseThrow(() -> new RuntimeException("Category not found")))
                                .collect(Collectors.toSet());
                        book.getCategories().clear();
                        book.setCategories(categories);
                    }
                    
                    return bookMapper.toDTO(bookRepository.save(book));
                });
    }
    
    @Transactional
    public boolean deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
