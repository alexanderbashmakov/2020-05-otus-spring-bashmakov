package ru.otus.library.service;

import de.vandermeer.asciitable.AsciiTable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.repository.BookRepository;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Класс BookServiceImpl:")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = BookServiceImpl.class)
class BookServiceImplTest {

    @Autowired
    private BookService service;

    @MockBean
    private BookRepository repository;

    @MockBean
    private IOService ioService;

    @MockBean
    private MessageBundleService messageBundleService;

    @DisplayName("сохраняет запись в БД")
    @Test
    void saveCreate() {
        Book book = Book.builder().
                name("testBook").
                author(Author.builder().name("testAuthor").build()).
                genre(Genre.builder().name("testGenre").build()).
                build();
        service.save(book);
        verify(repository, times(1)).insert(book);
    }

    @DisplayName("обновляет запись в БД")
    @Test
    void saveUpdate() {
        Book book = Book.builder().
                id(1L).
                name("testBook").
                author(Author.builder().name("testAuthor").build()).
                genre(Genre.builder().name("testGenre").build()).
                build();
        service.save(book);
        verify(repository, times(1)).update(book);
    }

    @DisplayName("выводит все записи")
    @Test
    void printAll() {
        Mockito.when(messageBundleService.getMessage("book.id")).thenReturn("book_id");
        Mockito.when(messageBundleService.getMessage("book.name")).thenReturn("book_name");
        Mockito.when(messageBundleService.getMessage("book.author")).thenReturn("author_name");
        Mockito.when(messageBundleService.getMessage("book.genre")).thenReturn("genre_name");
        Mockito.when(messageBundleService.getMessage("book.total")).thenReturn("book_total");

        List<Book> books = List.of(Book.builder().
                id(1L).
                name("testBook").
                author(Author.builder().name("testAuthor").build()).
                genre(Genre.builder().name("testGenre").build()).
                build());
        Mockito.when(repository.getAll()).thenReturn(books);
        Mockito.when(repository.count()).thenReturn(1);
        AsciiTable table = new AsciiTable();

        table.addRule();
        table.addRow("book_id", "book_name", "author_name", "genre_name");
        books.forEach(book -> {
            table.addRule();
            table.addRow(book.getId(), book.getName(), book.getAuthor().getName(), book.getGenre().getName());
        });
        table.addRule();

        service.printAll();
        verify(ioService).print(table.render());
        verify(ioService).print("book_total: 1");
    }

    @DisplayName("выводит запись по id")
    @Test
    void printBook() {
        Book book = Book.builder().
                id(1L).
                name("testBook").
                author(Author.builder().name("testAuthor").build()).
                genre(Genre.builder().name("testGenre").build()).
                build();
        Mockito.when(repository.getById(3L)).thenReturn(Optional.of(book));
        service.printBook(3L);
        verify(ioService).print(book.toString());
    }

    @DisplayName("удаляет запись по id")
    @Test
    void deleteById() {
        service.deleteById(1L);
        verify(repository, times(1)).deleteById(1L);
    }
}