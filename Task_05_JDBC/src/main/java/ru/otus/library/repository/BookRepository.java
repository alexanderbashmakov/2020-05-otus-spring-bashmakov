package ru.otus.library.repository;

import ru.otus.library.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    int count();
    Book insert(Book book);
    Book update(Book book);
    void deleteById(long id);
    Optional<Book> getById(long id);
    Optional<Book> getByName(String name);
    List<Book> getAll();

}
