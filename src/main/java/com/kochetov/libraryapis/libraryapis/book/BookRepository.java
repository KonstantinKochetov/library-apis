package com.kochetov.libraryapis.libraryapis.book;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<BookEntity, Integer> {
    List<BookEntity> findByTitleContaining(String title);
    BookEntity findByIsbn(String isbn);
}
