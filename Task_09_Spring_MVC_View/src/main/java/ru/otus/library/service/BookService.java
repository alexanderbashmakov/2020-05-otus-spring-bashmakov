package ru.otus.library.service;

import org.springframework.data.domain.Pageable;
import ru.otus.library.domain.Book;
import ru.otus.library.dto.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {
    void save(Book book);
    BookDto findById(String id);
    List<BookDto> findAll();
    void printAll(Pageable pageable);
    void printBook(String id);
    void deleteById(String id);
    void deleteAll();
}
