package ru.otus.library.service;

import ru.otus.library.domain.Genre;

public interface GenreService {
    void save(Genre genre);
    void printAll();
    void deleteById(Long id);
}
