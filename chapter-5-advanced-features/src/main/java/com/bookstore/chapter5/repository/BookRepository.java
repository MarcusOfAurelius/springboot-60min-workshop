package com.bookstore.chapter5.repository;

import com.bookstore.chapter5.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
    Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    
    Page<Book> findByAuthorContainingIgnoreCase(String author, Pageable pageable);
    
    /**
     * Find books with minimum rating
     */
    @Query("SELECT b FROM Book b WHERE b.averageRating >= :minRating ORDER BY b.averageRating DESC")
    List<Book> findTopRatedBooks(@Param("minRating") Double minRating);
    
    /**
     * Find bestsellers (books with low stock, indicating popularity)
     */
    @Query("SELECT b FROM Book b WHERE b.stock < :threshold AND b.stock > 0 ORDER BY b.stock ASC")
    List<Book> findBestsellers(@Param("threshold") Integer threshold);
    
    /**
     * Search books with pagination and sorting
     */
    @Query("SELECT b FROM Book b WHERE " +
           "LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(b.author) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(b.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Book> searchBooks(@Param("keyword") String keyword, Pageable pageable);
}
