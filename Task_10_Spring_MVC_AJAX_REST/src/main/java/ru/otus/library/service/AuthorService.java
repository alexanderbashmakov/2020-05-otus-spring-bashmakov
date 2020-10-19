package ru.otus.library.service;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.otus.library.domain.Author;
import ru.otus.library.dto.AuthorDto;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    void create(@NonNull String bookId, Author author);
    void update(@NonNull String id, Author author);
    List<AuthorDto> findAllByBook(String bookId);
    Optional<AuthorDto> findById(String id);
    void deleteById(String id);
    void deleteAll();
}
