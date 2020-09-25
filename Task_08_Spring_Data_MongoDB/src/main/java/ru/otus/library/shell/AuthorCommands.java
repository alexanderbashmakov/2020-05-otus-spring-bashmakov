package ru.otus.library.shell;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.library.domain.Author;
import ru.otus.library.service.AuthorService;

@ShellComponent
@AllArgsConstructor
public class AuthorCommands {
    private final AuthorService authorService;

    @ShellMethod(value = "AddAuthor", key = {"aa", "add-author"})
    public void addAuthor(String bookId, String name) {
        authorService.create(bookId, Author.builder().name(name).build());
    }

    @ShellMethod(value = "UpdateAuthor", key = {"ua", "update-author"})
    public void updateAuthor(String id, String name) {
        authorService.update(id, Author.builder().name(name).build());
    }

    @ShellMethod(value = "PrintAuthors", key = {"pa", "print-authors"})
    public void printAuthors(@ShellOption(defaultValue = "0") int page, @ShellOption(defaultValue = "10") int size) {
        authorService.printAll(PageRequest.of(page, size));
    }

    @ShellMethod(value = "PrintAuthors", key = {"pab", "print-authors-by-book"})
    public void printAuthors(String bookId, @ShellOption(defaultValue = "0") int page, @ShellOption(defaultValue = "10") int size) {
        authorService.printAll(PageRequest.of(page, size), bookId);
    }

    @ShellMethod(value = "DeleteAuthor", key = {"da", "delete-author"})
    public void deleteAuthor(String id) {
        authorService.deleteById(id);
    }

}
