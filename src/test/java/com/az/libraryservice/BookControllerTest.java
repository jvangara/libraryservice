package com.az.libraryservice;

import com.az.libraryservice.controller.BookController;
import com.az.libraryservice.exceptions.BookNotFoundException;
import com.az.libraryservice.model.Book;
import com.az.libraryservice.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @org.junit.jupiter.api.BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    public void testGetBookById_Found() throws Exception {
        String bookId = "1";
        Book book = Book.builder().id(bookId).build();

        when(bookService.getBookById(bookId)).thenReturn(book);

        mockMvc.perform(get("/api/books/{id}", bookId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(bookId));
    }

    @Test
    public void testGetBookById_NotFound() throws Exception {
        String bookId = "1";

        when(bookService.getBookById(bookId)).thenThrow(BookNotFoundException.class);

        mockMvc.perform(get("/api/books/{id}", bookId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteBookById() throws Exception {
        String bookId = "1";

        mockMvc.perform(delete("/api/books/{id}", bookId))
                .andExpect(status().isNoContent());

        verify(bookService, times(1)).deleteBookById(bookId);
    }

    @Test
    public void testFindByAuthor() throws Exception {
        List<Book> books = List.of(
                Book.builder().id("1").author("Author1").title("Book1").build(),
                Book.builder().id("2").author("Author1").title("Book2").build()
        );

        when(bookService.findByAuthor("Author1")).thenReturn(books);

        mockMvc.perform(get("/api/books/author/Author1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testFindByTitle() throws Exception {
        List<Book> books = List.of(Book.builder().id("1").title("Book1").publishedDate(LocalDate.parse("2018-05-15")).build(),
                Book.builder().id("2").title("Book1").publishedDate(LocalDate.parse("2024-05-12")).build());
        when(bookService.findByTitle("Book1")).thenReturn(books);
        mockMvc.perform(get("/api/books/title/Book1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void testCreateBook() throws Exception {
        Book book = Book.builder().id("123").title("Book1")
                .author("Author1").isbn("12345").build();

        when(bookService.saveBook(book)).thenReturn(book);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("123"))
                .andExpect(jsonPath("$.title").value("Book1"))
                .andExpect(jsonPath("$.author").value("Author1"))
                .andExpect(jsonPath("$.isbn").value("12345"));
    }

    @Test
    void testUpdateBook() throws Exception {
        Book book = Book.builder().id("123").title("Book1")
                .author("Author1").isbn("12345").build();

        when(bookService.saveBook(book)).thenReturn(book);

        mockMvc.perform(put("/api/books/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("123"))
                .andExpect(jsonPath("$.title").value("Book1"))
                .andExpect(jsonPath("$.author").value("Author1"))
                .andExpect(jsonPath("$.isbn").value("12345"));
    }

    @Test
    void testUpdateBookFailure() throws Exception {
        Book book = Book.builder().id("123").title("Book1")
                .author("Author1").isbn("12345").build();

        mockMvc.perform(put("/api/books/321")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Book Id in the path and body must match."));
    }

}