package com.bookstore.chapter3.service;

import com.bookstore.chapter3.dto.AuthorDTO;
import com.bookstore.chapter3.dto.CreateAuthorRequest;
import com.bookstore.chapter3.entity.Author;
import com.bookstore.chapter3.mapper.BookMapper;
import com.bookstore.chapter3.repository.AuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class AuthorService {
    
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;
    
    public AuthorService(AuthorRepository authorRepository, BookMapper bookMapper) {
        this.authorRepository = authorRepository;
        this.bookMapper = bookMapper;
    }
    
    public List<AuthorDTO> getAllAuthors() {
        return authorRepository.findAll().stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public Optional<AuthorDTO> getAuthorById(Long id) {
        // Use JOIN FETCH to load books in one query
        return authorRepository.findByIdWithBooks(id)
                .map(bookMapper::toDTO);
    }
    
    public List<AuthorDTO> searchAuthorsByName(String name) {
        return authorRepository.findByNameContainingIgnoreCase(name).stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public List<AuthorDTO> getAuthorsByCountry(String country) {
        return authorRepository.findByCountry(country).stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public AuthorDTO createAuthor(CreateAuthorRequest request) {
        Author author = new Author(
            request.getName(),
            request.getBiography(),
            request.getEmail(),
            request.getCountry()
        );
        Author saved = authorRepository.save(author);
        return bookMapper.toDTO(saved);
    }
    
    @Transactional
    public Optional<AuthorDTO> updateAuthor(Long id, CreateAuthorRequest request) {
        return authorRepository.findById(id)
                .map(author -> {
                    author.setName(request.getName());
                    author.setBiography(request.getBiography());
                    author.setEmail(request.getEmail());
                    author.setCountry(request.getCountry());
                    return bookMapper.toDTO(authorRepository.save(author));
                });
    }
    
    @Transactional
    public boolean deleteAuthor(Long id) {
        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
