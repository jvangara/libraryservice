package com.az.libraryservice.dto;

import com.az.libraryservice.model.Book;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class BookDTO {
    private String id;
    private String title;
    private LocalDate publishedDate;

    public static BookDTO fromEntity(Book book) {
        return BookDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .publishedDate(book.getPublishedDate())
                .build();
    }
}