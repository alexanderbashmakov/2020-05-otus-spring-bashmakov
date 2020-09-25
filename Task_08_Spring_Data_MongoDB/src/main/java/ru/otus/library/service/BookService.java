package ru.otus.library.service;

import org.springframework.data.domain.Pageable;
import ru.otus.library.domain.Book;

public interface BookService {
    void save(Book book);
    void printAll(Pageable pageable);
    void printBook(String id);
    void deleteById(String id);
    void deleteAll();
}
