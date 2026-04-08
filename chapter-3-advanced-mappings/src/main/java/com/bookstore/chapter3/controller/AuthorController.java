package com.bookstore.chapter3.controller;

import com.bookstore.chapter3.dto.AuthorDTO;
import com.bookstore.chapter3.dto.CreateAuthorRequest;
import com.bookstore.chapter3.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {
    
    private final AuthorService authorService;
    
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }
    
    @GetMapping
    public List<AuthorDTO> getAllAuthors() {
        return authorService.getAllAuthors();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable Long id) {
        return authorService.getAuthorById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/search")
    public List<AuthorDTO> searchAuthors(@RequestParam String name) {
        return authorService.searchAuthorsByName(name);
    }
    
    @GetMapping("/country/{country}")
    public List<AuthorDTO> getAuthorsByCountry(@PathVariable String country) {
        return authorService.getAuthorsByCountry(country);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDTO createAuthor(@RequestBody CreateAuthorRequest request) {
        return authorService.createAuthor(request);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<AuthorDTO> updateAuthor(@PathVariable Long id, @RequestBody CreateAuthorRequest request) {
        return authorService.updateAuthor(id, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        return authorService.deleteAuthor(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
