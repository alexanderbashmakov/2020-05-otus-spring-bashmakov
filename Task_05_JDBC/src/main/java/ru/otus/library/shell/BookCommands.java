package ru.otus.library.shell;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.service.BookService;

@ShellComponent
@AllArgsConstructor
public class BookCommands {
    private final BookService bookService;

    @ShellMethod(value = "AddBook", key = {"ab", "add-book"})
    public void addBook(String name, String author, String genre) {
        bookService.save(Book.builder().
                name(name).
                author(Author.builder().name(author).build()).
                genre(Genre.builder().name(genre).build()).
                build());
    }

    @ShellMethod(value = "UpdateBook", key = {"ub", "update-book"})
    public void updateBook(Long id, String name, String author, String genre) {
        bookService.save(Book.builder().
                id(id).
                name(name).
                author(Author.builder().name(author).build()).
                genre(Genre.builder().name(genre).build()).
                build());
    }

    @ShellMethod(value = "PrintBooks", key = {"pb", "print-books"})
    public void printBooks() {
        bookService.printAll();
    }

    @ShellMethod(value = "DeleteBook", key = {"db", "delete-book"})
    public void deleteBook(Long id) {
        bookService.deleteById(id);
    }

    @ShellMethod(value = "FindBook", key = {"fb", "find-book"})
    public void findBook(Long id) {
        bookService.printBook(id);
    }
}
