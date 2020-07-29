package ru.otus.library.shell;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.library.domain.Author;
import ru.otus.library.service.AuthorService;

@ShellComponent
@AllArgsConstructor
public class AuthorCommands {
    private final AuthorService authorService;

    @ShellMethod(value = "AddAuthor", key = {"aa", "add-author"})
    public void addAuthor(String name) {
        authorService.save(Author.builder().name(name).build());
    }

    @ShellMethod(value = "UpdateAuthor", key = {"ua", "update-author"})
    public void updateAuthor(Long id, String name) {
        authorService.save(Author.builder().id(id).name(name).build());
    }

    @ShellMethod(value = "PrintAuthors", key = {"pa", "print-authors"})
    public void printAuthors() {
        authorService.printAll();
    }

    @ShellMethod(value = "DeleteAuthor", key = {"da", "delete-author"})
    public void deleteAuthor(Long id) {
        authorService.deleteById(id);
    }

}
