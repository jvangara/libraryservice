package com.az.libraryservice;

import com.az.libraryservice.exceptions.BookNotFoundException;
import com.az.libraryservice.model.Book;
import com.az.libraryservice.repository.BookRepository;
import com.az.libraryservice.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Test
    public void testGetBookById_Found() {
        String bookId = "1";
        Book book = Book.builder().id(bookId).build();
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        Book result = bookService.getBookById(bookId);
        assertEquals(book, result);
    }

    @Test
    public void testGetBookById_NotFound() {
        String bookId = "1";
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());
        assertThrows(BookNotFoundException.class, () -> bookService.getBookById(bookId));
    }

    @Test
    public void testDeleteBookById() {
        String bookId = "1";
        bookService.deleteBookById(bookId);
        verify(bookRepository, times(1)).deleteById(bookId);
    }

    @Test
    public void testSearchBooks() {
        Page<Book> bookPage = new PageImpl<>(List.of(Book.builder().title("Test Book1").build(), Book.builder().author("Test Author2").build()));
        when(bookRepository.findByTitleContainingOrAuthorContaining(anyString(), anyString(), any(PageRequest.class))).thenReturn(bookPage);
        Page<Book> result = bookService.searchBooks("Test", PageRequest.of(0, 10));
        assertEquals(bookPage.getContent(), result.getContent());
    }

    @Test
    public void testFindByAuthor() {
        List<Book> books = List.of(
                Book.builder().author("Author1").title("Book1").build(),
                Book.builder().author("Author1").title("Book2").build()
        );

        when(bookRepository.findByAuthor("Author1")).thenReturn(books);
        List<Book> result = bookService.findByAuthor("Author1");
        assertEquals(books, result);
    }

    @Test
    public void testFindByTitle() {
        List<Book> books = List.of(Book.builder().title("Book1").author("Author1").build(), Book.builder().title("Book1").author("Author2").build());
        when(bookRepository.findByTitle("Book1")).thenReturn(books);
        List<Book> result = bookService.findByTitle("Book1");
        assertEquals(books, result);
    }

    @Test
    void testSaveBook() {
        Book book = Book.builder().id("123").title("Book1")
                .author("Author1").isbn("12345").build();

        when(bookRepository.save(book)).thenReturn(book);
        Book savedBook = bookService.saveBook(book);

        verify(bookRepository).save(book);
        assertEquals(book, savedBook);
    }

}