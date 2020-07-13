package ru.otus.library.repository;

import ru.otus.library.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    Author insert(Author author);
    Author update(Author author);
    void deleteById(Long id);
    Optional<Author> getById(Long id);
    Optional<Author> getByName(String name);
    List<Author> getAll();
}
