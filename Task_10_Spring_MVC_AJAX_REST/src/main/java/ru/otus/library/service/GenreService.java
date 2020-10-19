package ru.otus.library.service;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.otus.library.domain.Genre;
import ru.otus.library.dto.GenreDto;

import java.util.List;
import java.util.Optional;

public interface GenreService {
    void create(@NonNull String bookId, Genre genre);
    void update(@NonNull String id, Genre genre);
    List<GenreDto> findAllByBook(String bookId);
    Optional<GenreDto> findById(String id);
    void deleteById(String id);
    void deleteAll();
}
