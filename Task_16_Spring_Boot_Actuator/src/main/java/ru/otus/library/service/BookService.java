package ru.otus.library.service;

import ru.otus.library.domain.Book;

public interface BookService {
    void save(Book book);
    void printAll();
    void printBook(Long id);
    void deleteById(Long id);
}
