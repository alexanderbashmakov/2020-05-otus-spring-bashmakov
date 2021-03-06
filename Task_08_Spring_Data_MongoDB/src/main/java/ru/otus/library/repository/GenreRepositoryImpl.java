package ru.otus.library.repository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Genre;
import ru.otus.library.dto.GenreDto;

@RequiredArgsConstructor
@Repository
public class GenreRepositoryImpl implements GenreRepository {

    private final BookOperations bookOperations;
    private static final String ARRAY_NAME = "genres";

    @Override
    public void create(@NonNull String bookId, Genre genre) {
        bookOperations.createElement(bookId, genre, ARRAY_NAME);
    }

    @Override
    public void update(@NonNull String id, Genre genre) {
        bookOperations.updateElement(id, genre, ARRAY_NAME);
    }

    @Override
    public Page<GenreDto> findGenresByBookId(Pageable pageable, String bookId) {
        return bookOperations.findElements(pageable, bookId, GenreDto.class, ARRAY_NAME);
    }

    @Override
    public Page<GenreDto> findGenres(Pageable pageable) {
        return bookOperations.findElements(pageable, null, GenreDto.class, ARRAY_NAME);
    }

    @Override
    public void deleteById(String id) {
        bookOperations.deleteById(id, ARRAY_NAME);
    }

    @Override
    public void deleteAll() {
        bookOperations.deleteAll(ARRAY_NAME);
    }
}
