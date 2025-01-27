package com.az.libraryservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "books")
public class Book {
    @Id
    private String id = UUID.randomUUID().toString();

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    @NotNull
    private LocalDate publishedDate;

    @NotBlank
    private String isbn;

}
