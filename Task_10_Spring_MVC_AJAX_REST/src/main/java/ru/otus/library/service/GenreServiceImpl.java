package ru.otus.library.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Genre;
import ru.otus.library.dto.GenreDto;
import ru.otus.library.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Transactional
    @Override
    public void create(@NonNull String bookId, Genre genre) {
        genreRepository.create(bookId, genre);
    }

    @Transactional
    @Override
    public void update(@NonNull String id, Genre genre) {
        genreRepository.update(id, genre);
    }

    @Transactional
    @Override
    public List<GenreDto> findAllByBook(String bookId) {
        return genreRepository.findGenresByBookId(bookId);
    }

    @Transactional
    @Override
    public Optional<GenreDto> findById(String id) {
        return genreRepository.findById(id);
    }

    @Transactional
    @Override
    public void deleteById(String id) {
        genreRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteAll() {
        genreRepository.deleteAll();
    }
}
