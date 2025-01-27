package com.az.libraryservice.repository;

import com.az.libraryservice.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {

    Page<Book> findByTitleContainingOrAuthorContaining(String title, String author, Pageable pageable);

    @Query("{ 'author' : ?0 }")
    List<Book> findByAuthor(String author);

    @Query("{ 'title' : ?0 }")
    List<Book> findByTitle(String title);
}
