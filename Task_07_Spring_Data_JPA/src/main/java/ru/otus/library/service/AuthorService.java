package ru.otus.library.service;

import ru.otus.library.domain.Author;

public interface AuthorService {
    void save(Author author);
    void printAll();
    void deleteById(Long id);
}
