package ru.otus.library.service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Author;
import ru.otus.library.dto.AuthorDto;
import ru.otus.library.repository.AuthorRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Transactional
    @Override
    public void create(@NonNull String bookId, Author author) {
        authorRepository.create(bookId, author);
    }

    @Transactional
    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void update(@NonNull String id, Author author) {
        authorRepository.update(id, author);
    }

    @Transactional
    @Override
    public Page<AuthorDto> findAllByBook(Pageable pageable, String bookId) {
        return authorRepository.findAuthors(pageable, bookId);
    }

    @Transactional
    @Override
    public Optional<AuthorDto> findById(String id) {
        return authorRepository.findById(id);
    }

    @Transactional
    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteById(String id) {
        authorRepository.deleteById(id);
    }

    @Transactional
    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteAll() {
        authorRepository.deleteAll();
    }
}
