package ru.otus.library.repository;

import ru.otus.library.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Long count();
    Book save(Book book);
    void deleteById(Long id);
    Optional<Book> getById(Long id);
    Optional<Book> getByName(String name);
    List<Book> getAll();

}
