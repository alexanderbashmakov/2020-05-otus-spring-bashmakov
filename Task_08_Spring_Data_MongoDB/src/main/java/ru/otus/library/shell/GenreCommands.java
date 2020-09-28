package ru.otus.library.shell;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.library.domain.Genre;
import ru.otus.library.service.GenreService;

@ShellComponent
@AllArgsConstructor
public class GenreCommands {
    private final GenreService genreService;

    @ShellMethod(value = "AddGenre", key = {"ag", "add-genre"})
    public void addGenre(String bookId, String name) {
        genreService.create(bookId, Genre.builder().name(name).build());
    }

    @ShellMethod(value = "UpdateGenre", key = {"ug", "update-genre"})
    public void updateGenre(String id, String name) {
        genreService.update(id, Genre.builder().id(id).name(name).build());
    }

    @ShellMethod(value = "PrintGenres", key = {"pg", "print-genres"})
    public void printGenres(@ShellOption(defaultValue = "0") int page, @ShellOption(defaultValue = "10") int size) {
        genreService.printAll(PageRequest.of(page, size));
    }

    @ShellMethod(value = "PrintGenres", key = {"pgb", "print-genres-by-book"})
    public void printGenres(String bookId, @ShellOption(defaultValue = "0") int page, @ShellOption(defaultValue = "10") int size) {
        genreService.printAll(PageRequest.of(page, size), bookId);
    }

    @ShellMethod(value = "DeleteGenre", key = {"dg", "delete-genre"})
    public void deleteGenre(String id) {
        genreService.deleteById(id);
    }

}
