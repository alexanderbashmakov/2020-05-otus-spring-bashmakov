package ru.otus.library.repository;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Genre;
import ru.otus.library.dto.GenreDto;

import java.util.Optional;

public interface GenreRepository {
    void create(@NonNull String bookId, Genre genre);
    void update(@NonNull String id, Genre genre);
    Optional<GenreDto> findById(@NonNull String id);
    Page<GenreDto> findGenres(Pageable pageable);
    Page<GenreDto> findGenresByBookId(Pageable pageable, String bookId);
    void deleteById(String id);
    void deleteAll();
}
