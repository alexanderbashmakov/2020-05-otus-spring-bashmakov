package ru.otus.library.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Book;
import ru.otus.library.dto.BookDto;
import ru.otus.library.exceptions.EntityNotFound;
import ru.otus.library.repository.BookRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final IOService ioService;
    private final MessageBundleService messages;

    @Transactional
    @Override
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional(readOnly = true)
    @Override
    public void printAll(Pageable pageable) {
    }

    @Transactional
    @Override
    public BookDto findById(String bookId){
        Book book = bookRepository.findById(bookId).orElseThrow(EntityNotFound::new);
        return BookDto.toDto(book);
    }

    @Transactional
    @Override
    public List<BookDto> findAll(){
        return bookRepository.findAll().stream().map(BookDto::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public void printBook(String id) {
        Book book = bookRepository.findById(id).orElseThrow(EntityNotFound::new);
        ioService.print(book.toString());
    }

    @Transactional
    @Override
    public void deleteById(String id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteAll() {
        bookRepository.deleteAll();
    }
}
