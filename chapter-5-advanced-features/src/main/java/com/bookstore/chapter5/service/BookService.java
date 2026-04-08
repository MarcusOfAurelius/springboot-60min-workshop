package com.bookstore.chapter5.service;

import com.bookstore.chapter5.entity.Book;
import com.bookstore.chapter5.entity.Review;
import com.bookstore.chapter5.exception.BadRequestException;
import com.bookstore.chapter5.exception.ResourceNotFoundException;
import com.bookstore.chapter5.repository.BookRepository;
import com.bookstore.chapter5.repository.ReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookService {
    
    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;
    
    public BookService(BookRepository bookRepository, ReviewRepository reviewRepository) {
        this.bookRepository = bookRepository;
        this.reviewRepository = reviewRepository;
    }
    
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    
    /**
     * Get books with pagination and sorting
     * Example: page=0, size=10, sort=title,asc
     */
    public Page<Book> getBooksPageable(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }
    
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));
    }
    
    /**
     * Search books with pagination
     */
    public Page<Book> searchBooks(String keyword, Pageable pageable) {
        return bookRepository.searchBooks(keyword, pageable);
    }
    
    /**
     * Get top-rated books
     */
    public List<Book> getTopRatedBooks(Double minRating) {
        return bookRepository.findTopRatedBooks(minRating);
    }
    
    /**
     * Get bestsellers
     */
    public List<Book> getBestsellers(Integer stockThreshold) {
        return bookRepository.findBestsellers(stockThreshold);
    }
    
    @Transactional
    public Book createBook(Book book) {
        // Validation is already handled by @Valid in controller
        return bookRepository.save(book);
    }
    
    @Transactional
    public Book updateBook(Long id, Book bookDetails) {
        Book book = getBookById(id);
        
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setIsbn(bookDetails.getIsbn());
        book.setPrice(bookDetails.getPrice());
        book.setStock(bookDetails.getStock());
        book.setDescription(bookDetails.getDescription());
        
        return bookRepository.save(book);
    }
    
    @Transactional
    public void deleteBook(Long id) {
        Book book = getBookById(id);
        bookRepository.delete(book);
    }
    
    /**
     * Add a review to a book
     */
    @Transactional
    public Review addReview(Long bookId, Review review) {
        Book book = getBookById(bookId);
        
        review.setBook(book);
        Review savedReview = reviewRepository.save(review);
        
        // Update average rating
        book.addReview(savedReview);
        bookRepository.save(book);
        
        return savedReview;
    }
    
    /**
     * Get all reviews for a book
     */
    public List<Review> getBookReviews(Long bookId) {
        // Verify book exists
        getBookById(bookId);
        return reviewRepository.findByBookIdOrderByCreatedAtDesc(bookId);
    }
}
