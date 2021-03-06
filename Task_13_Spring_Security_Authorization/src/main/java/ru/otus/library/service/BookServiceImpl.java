package ru.otus.library.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Book;
import ru.otus.library.dto.BookDto;
import ru.otus.library.exceptions.EntityNotFound;
import ru.otus.library.repository.BookRepository;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final MessageBundleService messages;

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    @Override
    public BookDto findById(String bookId){
        Book book = bookRepository.findById(bookId).orElseThrow(EntityNotFound::new);
        return BookDto.toDto(book);
    }

    @Transactional
    @Override
    public Page<BookDto> findAll(Pageable pageable){
        return bookRepository.findAll(pageable).map(BookDto::toDto);
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public void deleteById(String id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public void deleteAll() {
        bookRepository.deleteAll();
    }
}
