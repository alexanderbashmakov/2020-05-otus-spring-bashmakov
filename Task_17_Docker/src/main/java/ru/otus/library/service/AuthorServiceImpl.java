package ru.otus.library.service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Author;
import ru.otus.library.dto.AuthorDto;
import ru.otus.library.repository.AuthorRepository;

import java.util.List;
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
    public void update(@NonNull String id, Author author) {
        authorRepository.update(id, author);
    }

    @Transactional
    @Override
    public List<AuthorDto> findAllByBook(String bookId) {
        return authorRepository.findAuthors(bookId);
    }

    @Transactional
    @Override
    public Optional<AuthorDto> findById(String id) {
        return authorRepository.findById(id);
    }

    @Transactional
    @Override
    public void deleteById(String id) {
        authorRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteAll() {
        authorRepository.deleteAll();
    }
}
