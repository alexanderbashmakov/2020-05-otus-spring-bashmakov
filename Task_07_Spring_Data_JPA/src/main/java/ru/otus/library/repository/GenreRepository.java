package ru.otus.library.repository;

import ru.otus.library.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    Genre save(Genre genre);
    void deleteById(Long id);
    Optional<Genre> getById(Long id);
    Optional<Genre> getByName(String name);
    List<Genre> getAll();
}
