package com.az.libraryservice.controller;

import com.az.libraryservice.dto.BookDTO;
import com.az.libraryservice.exceptions.BookNotFoundException;
import com.az.libraryservice.model.Book;
import com.az.libraryservice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Validated
public class BookController {

    @Autowired
    private BookService bookService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
        return new ResponseEntity<>(bookService.saveBook(book), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<BookDTO>> getAllBooks(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> bookPage = bookService.getAllBooks(pageable);
        return ResponseEntity.ok(bookPage.map(BookDTO::fromEntity));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable String id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<BookDTO>> searchBooks(@RequestParam String query,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> bookPage = bookService.searchBooks(query, pageable);
        return ResponseEntity.ok(bookPage.map(BookDTO::fromEntity));
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<List<Book>> findByAuthor(@PathVariable String author) {
        return ResponseEntity.ok(bookService.findByAuthor(author));
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<Book>> findByTitle(@PathVariable String title) {
        return ResponseEntity.ok(bookService.findByTitle(title));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateBook(@PathVariable String id, @Valid @RequestBody Book book) {
        if (!id.equals(book.getId())) {
            return ResponseEntity.badRequest().body("Book Id in the path and body must match.");
        }
        return ResponseEntity.ok(bookService.saveBook(book));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookById(@PathVariable String id) {
        bookService.deleteBookById(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<String> handleBookNotFoundException(BookNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}

