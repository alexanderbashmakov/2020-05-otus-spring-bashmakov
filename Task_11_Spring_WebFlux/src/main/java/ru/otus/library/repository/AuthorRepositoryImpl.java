package ru.otus.library.repository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Author;
import ru.otus.library.dto.AuthorDto;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class AuthorRepositoryImpl implements AuthorRepository {
    private final BookOperations bookOperations;
    private static final String ARRAY_NAME = "authors";

    @Override
    public void create(@NonNull String bookId, Author author) {
        bookOperations.createElement(bookId, author, ARRAY_NAME);
    }

    @Override
    public void update(@NonNull String id, Author author) {
        bookOperations.updateElement(id, author, ARRAY_NAME);
    }

    @Override
    public Optional<AuthorDto> findById(String id){
        return Optional.ofNullable(bookOperations.findById(id, AuthorDto.class, ARRAY_NAME));
    }

    @Override
    public List<AuthorDto> findAuthors() {
        return bookOperations.findElements(null, AuthorDto.class, ARRAY_NAME);
    }

    @Override
    public List<AuthorDto> findAuthors(String bookId) {
        return bookOperations.findElements(bookId, AuthorDto.class, ARRAY_NAME);
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
