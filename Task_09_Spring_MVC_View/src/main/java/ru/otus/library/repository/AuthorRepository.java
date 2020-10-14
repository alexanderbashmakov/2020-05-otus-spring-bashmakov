package ru.otus.library.repository;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.otus.library.domain.Author;
import ru.otus.library.dto.AuthorDto;

import java.util.Optional;

public interface AuthorRepository {
    void create(@NonNull String bookId, Author author);
    void update(@NonNull String id, Author author);
    Optional<AuthorDto> findById(@NonNull String id);
    Page<AuthorDto> findAuthors(Pageable pageable);
    Page<AuthorDto> findAuthors(Pageable pageable, String bookId);
    void deleteById(String id);
    void deleteAll();
}
