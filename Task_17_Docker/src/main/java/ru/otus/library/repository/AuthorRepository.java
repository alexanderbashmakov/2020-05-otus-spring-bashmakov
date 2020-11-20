package ru.otus.library.repository;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.otus.library.domain.Author;
import ru.otus.library.dto.AuthorDto;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    void create(@NonNull String bookId, Author author);
    void update(@NonNull String id, Author author);
    Optional<AuthorDto> findById(@NonNull String id);
    List<AuthorDto> findAuthors();
    List<AuthorDto> findAuthors(String bookId);
    void deleteById(String id);
    void deleteAll();
}
