package ru.otus.library.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.otus.library.domain.Book;
import ru.otus.library.dto.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {
    void save(Book book);
    BookDto findById(String id);
    Page<BookDto> findAll(Pageable pageable);
    void printBook(String id);
    void deleteById(String id);
    void deleteAll();
}
