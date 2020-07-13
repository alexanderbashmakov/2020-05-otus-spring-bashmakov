package ru.otus.library.service;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.document.TableRowStyle;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.exceptions.EntityNotFound;
import ru.otus.library.repository.AuthorRepository;
import ru.otus.library.repository.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final IOService ioService;
    private final MessageBundleService messages;

    @Override
    public void save(Book book) {
        if (book.getId() == null) {
            bookRepository.insert(book);
        } else {
            bookRepository.update(book);
        }
    }

    @Override
    public void printAll() {
        List<Book> books = bookRepository.getAll();
        AsciiTable table = new AsciiTable();
        table.addRule();
        table.addRow(
                messages.getMessage("book.id"),
                messages.getMessage("book.name"),
                messages.getMessage("book.author"),
                messages.getMessage("book.genre"));

        books.forEach(book -> {
            table.addRule();
            table.addRow(book.getId(), book.getName(), book.getAuthor().getName(), book.getGenre().getName());
        });
        table.addRule();
        ioService.print(table.render());
        ioService.print(String.format("%s: %d", messages.getMessage("book.total"), bookRepository.count()));
    }

    @Override
    public void printBook(Long id) {
        Book book = bookRepository.getById(id).orElseThrow(EntityNotFound::new);
        ioService.print(book.toString());
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}
