package com.bookstore.chapter3.repository;

import com.bookstore.chapter3.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
    List<Book> findByTitleContainingIgnoreCase(String title);
    
    List<Book> findByAuthorId(Long authorId);
    
    List<Book> findByAuthorName(String authorName);
    
    Optional<Book> findByIsbn(String isbn);
    
    List<Book> findByPriceLessThan(Double price);
    
    /**
     * Find books by category name
     * This demonstrates querying through a Many-to-Many relationship
     */
    @Query("SELECT b FROM Book b JOIN b.categories c WHERE c.name = :categoryName")
    List<Book> findByCategoryName(@Param("categoryName") String categoryName);
    
    /**
     * Find book with author (using JOIN FETCH to avoid N+1)
     */
    @Query("SELECT b FROM Book b JOIN FETCH b.author WHERE b.id = :id")
    Optional<Book> findByIdWithAuthor(@Param("id") Long id);
    
    /**
     * Find book with all relationships loaded
     */
    @Query("SELECT DISTINCT b FROM Book b " +
           "LEFT JOIN FETCH b.author " +
           "LEFT JOIN FETCH b.categories " +
           "WHERE b.id = :id")
    Optional<Book> findByIdWithDetails(@Param("id") Long id);
}
