package ru.otus.library.shell;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.service.BookService;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ShellComponent
@AllArgsConstructor
public class BookCommands {
    private final BookService bookService;

    @ShellMethod(value = "AddBook", key = {"ab", "add-book"})
    public void addBook(String name, String[] authors, String[] genres) {
        bookService.save(Book.builder()
                .name(name)
                .authors(Stream.of(authors).map(author -> Author.builder().id(UUID.randomUUID().toString()).name(author).build()).collect(Collectors.toList()))
                .genres(Stream.of(genres).map(genre -> Genre.builder().id(UUID.randomUUID().toString()).name(genre).build()).collect(Collectors.toList()))
                .build());
    }

    @ShellMethod(value = "UpdateBook", key = {"ub", "update-book"})
    public void updateBook(String id, String name, String[] authors, String[] genres) {
        bookService.save(Book.builder()
                .id(id)
                .name(name)
                .authors(Stream.of(authors).map(author -> Author.builder().id(UUID.randomUUID().toString()).name(author).build()).collect(Collectors.toList()))
                .genres(Stream.of(genres).map(genre -> Genre.builder().id(UUID.randomUUID().toString()).name(genre).build()).collect(Collectors.toList()))
                .build());
    }

    @ShellMethod(value = "PrintBooks", key = {"pb", "print-books"})
    public void printBooks(@ShellOption(defaultValue = "0") int page, @ShellOption(defaultValue = "10") int size) {
        bookService.printAll(PageRequest.of(page, size));
    }

    @ShellMethod(value = "DeleteBook", key = {"db", "delete-book"})
    public void deleteBook(String id) {
        bookService.deleteById(id);
    }

    @ShellMethod(value = "FindBook", key = {"fb", "find-book"})
    public void findBook(String id) {
        bookService.printBook(id);
    }
}
