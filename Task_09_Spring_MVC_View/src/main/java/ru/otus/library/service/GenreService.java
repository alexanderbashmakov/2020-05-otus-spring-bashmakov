package ru.otus.library.service;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.otus.library.domain.Genre;
import ru.otus.library.dto.GenreDto;

public interface GenreService {
    void create(@NonNull String bookId, Genre genre);
    void update(@NonNull String id, Genre genre);
    void printAll(Page<GenreDto> page);
    void printAll(Pageable pageable, String bookId);
    void printAll(Pageable pageable);
    void deleteById(String id);
    void deleteAll();
}
