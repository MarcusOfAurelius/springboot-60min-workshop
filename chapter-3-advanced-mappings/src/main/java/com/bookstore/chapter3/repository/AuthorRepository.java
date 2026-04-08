package com.bookstore.chapter3.repository;

import com.bookstore.chapter3.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    
    Optional<Author> findByName(String name);
    
    List<Author> findByCountry(String country);
    
    List<Author> findByNameContainingIgnoreCase(String name);
    
    /**
     * Custom query with JOIN FETCH to avoid N+1 query problem
     * This loads the author with all their books in a single query
     */
    @Query("SELECT DISTINCT a FROM Author a LEFT JOIN FETCH a.books WHERE a.id = :id")
    Optional<Author> findByIdWithBooks(Long id);
}
