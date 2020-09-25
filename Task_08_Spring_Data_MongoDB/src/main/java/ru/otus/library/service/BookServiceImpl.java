package ru.otus.library.service;

import de.vandermeer.asciitable.AsciiTable;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.exceptions.EntityNotFound;
import ru.otus.library.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        AsciiTable table = new AsciiTable();
        table.addRule();
        table.addRow(
                messages.getMessage("book.id"),
                messages.getMessage("book.name"),
                messages.getMessage("book.authors"),
                messages.getMessage("book.genres"));

        Page<Book> books = bookRepository.findAll(pageable);

        books.forEach(book -> {
            List<Author> authors = Optional.ofNullable(book.getAuthors()).orElse(new ArrayList<>());
            List<Genre> genres = Optional.ofNullable(book.getGenres()).orElse(new ArrayList<>());
            table.addRule();
            table.addRow(book.getId(), book.getName(),
                    authors.stream().map(Author::getName).collect(Collectors.joining("; ")),
                    genres.stream().map(Genre::getName).collect(Collectors.joining("; ")));
        });

        table.addRule();
        ioService.print(table.render());
        ioService.print(messages.getMessage("page", books.getNumber() + 1, books.getTotalPages()));
        ioService.print(messages.getMessage("total", books.getTotalElements()));
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
