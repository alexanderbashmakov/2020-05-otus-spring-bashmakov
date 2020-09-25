package ru.otus.library.service;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.otus.library.domain.Author;
import ru.otus.library.dto.AuthorDto;

public interface AuthorService {
    void create(@NonNull String bookId, Author author);
    void update(@NonNull String id, Author author);
    void printAll(Page<AuthorDto> page);
    void printAll(Pageable pageable, String bookId);
    void printAll(Pageable pageable);
    void deleteById(String id);
    void deleteAll();
}
