package ru.otus.library.service;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.otus.library.domain.Author;
import ru.otus.library.dto.AuthorDto;

import java.util.Optional;

public interface AuthorService {
    void create(@NonNull String bookId, Author author);
    void update(@NonNull String id, Author author);
    Page<AuthorDto> findAllByBook(Pageable pageable, String bookId);
    Optional<AuthorDto> findById(String id);
    void deleteById(String id);
    void deleteAll();
}
