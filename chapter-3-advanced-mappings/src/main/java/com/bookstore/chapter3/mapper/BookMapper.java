package com.bookstore.chapter3.mapper;

import com.bookstore.chapter3.dto.AuthorDTO;
import com.bookstore.chapter3.dto.BookDTO;
import com.bookstore.chapter3.entity.Author;
import com.bookstore.chapter3.entity.Book;
import com.bookstore.chapter3.entity.Category;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * BookMapper - converts between Entity and DTO
 * 
 * This is a manual mapper. In real projects, you might use:
 * - MapStruct (compile-time code generation)
 * - ModelMapper (reflection-based)
 * 
 * Manual mapping gives you full control and is easy to understand for learning!
 */
@Component
public class BookMapper {
    
    /**
     * Convert Book entity to BookDTO
     */
    public BookDTO toDTO(Book book) {
        if (book == null) {
            return null;
        }
        
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setIsbn(book.getIsbn());
        dto.setPrice(book.getPrice());
        dto.setStock(book.getStock());
        dto.setDescription(book.getDescription());
        
        // Map author information
        if (book.getAuthor() != null) {
            dto.setAuthorId(book.getAuthor().getId());
            dto.setAuthorName(book.getAuthor().getName());
        }
        
        // Map category names
        if (book.getCategories() != null) {
            dto.setCategories(
                book.getCategories().stream()
                    .map(Category::getName)
                    .collect(Collectors.toSet())
            );
        }
        
        return dto;
    }
    
    /**
     * Convert Author entity to AuthorDTO
     */
    public AuthorDTO toDTO(Author author) {
        if (author == null) {
            return null;
        }
        
        AuthorDTO dto = new AuthorDTO();
        dto.setId(author.getId());
        dto.setName(author.getName());
        dto.setBiography(author.getBiography());
        dto.setEmail(author.getEmail());
        dto.setCountry(author.getCountry());
        
        // Map book summaries
        if (author.getBooks() != null) {
            dto.setBooks(
                author.getBooks().stream()
                    .map(book -> new AuthorDTO.BookSummaryDTO(
                        book.getId(),
                        book.getTitle(),
                        book.getIsbn(),
                        book.getPrice(),
                        book.getStock()
                    ))
                    .collect(Collectors.toList())
            );
        }
        
        return dto;
    }
}
