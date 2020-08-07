package ru.otus.library.service;

import de.vandermeer.asciitable.AsciiTable;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Book;
import ru.otus.library.exceptions.EntityNotFound;
import ru.otus.library.repository.BookRepository;

import java.util.List;

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
    public void printAll() {
        AsciiTable table = new AsciiTable();
        table.addRule();
        table.addRow(
                messages.getMessage("book.id"),
                messages.getMessage("book.name"),
                messages.getMessage("book.author"),
                messages.getMessage("book.genre"));

        bookRepository.findAll().forEach(book -> {
            table.addRule();
            table.addRow(book.getId(), book.getName(), book.getAuthor().getName(), book.getGenre().getName());
        });
        table.addRule();
        ioService.print(table.render());
        ioService.print(String.format("%s: %d", messages.getMessage("book.total"), bookRepository.count()));
    }

    @Transactional(readOnly = true)
    @Override
    public void printBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(EntityNotFound::new);
        ioService.print(book.toString());
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}
