package com.bookstore.chapter2.repository;

import com.bookstore.chapter2.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * BookRepository - Spring Data JPA Repository
 * 
 * By extending JpaRepository, we get LOTS of methods for free:
 * - save(entity)
 * - findById(id)
 * - findAll()
 * - deleteById(id)
 * - count()
 * - existsById(id)
 * - and many more!
 * 
 * Spring Data also supports query methods - methods that generate queries from their names!
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
    /**
     * Query Methods - Spring generates the SQL from the method name!
     * 
     * Naming pattern: findBy + PropertyName + Operator
     * Examples:
     * - findByTitle -> WHERE title = ?
     * - findByTitleContaining -> WHERE title LIKE %?%
     * - findByPriceLessThan -> WHERE price < ?
     */
    
    // Find books by exact title
    Optional<Book> findByTitle(String title);
    
    // Find books where title contains the search term (case-insensitive)
    List<Book> findByTitleContainingIgnoreCase(String title);
    
    // Find books by author
    List<Book> findByAuthor(String author);
    
    // Find books by genre
    List<Book> findByGenre(String genre);
    
    // Find books by ISBN
    Optional<Book> findByIsbn(String isbn);
    
    // Find books with price less than specified amount
    List<Book> findByPriceLessThan(Double price);

    // Find books with price greater than specified amount
    List<Book> findByPriceGreaterThan(Double price);

    // Find books with price between range
    List<Book> findByPriceBetween(Double minPrice, Double maxPrice);
    
    // Find books with stock greater than specified amount
    List<Book> findByStockGreaterThan(Integer stock);
    
    // Find books by genre ordered by price
    List<Book> findByGenreOrderByPriceAsc(String genre);
    
    /**
     * Custom JPQL Query
     * 
     * @Query lets you write your own queries in JPQL (Java Persistence Query Language)
     * JPQL is similar to SQL but works with entities instead of tables
     */
    @Query("SELECT b FROM Book b WHERE b.author = :author AND b.price < :maxPrice")
    List<Book> findAffordableBooksByAuthor(@Param("author") String author, @Param("maxPrice") Double maxPrice);
    
    /**
     * Native SQL Query
     * 
     * You can also use native SQL queries when needed
     */
    @Query(value = "SELECT * FROM books WHERE stock < :threshold ORDER BY stock ASC", nativeQuery = true)
    List<Book> findLowStockBooks(@Param("threshold") Integer threshold);
}
