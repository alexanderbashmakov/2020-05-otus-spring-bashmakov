package ru.otus.library.shell;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.library.domain.Genre;
import ru.otus.library.service.GenreService;

@ShellComponent
@AllArgsConstructor
public class GenreCommands {
    private final GenreService genreService;

    @ShellMethod(value = "AddGenre", key = {"ag", "add-genre"})
    public void addGenre(String name) {
        genreService.save(Genre.builder().name(name).build());
    }

    @ShellMethod(value = "UpdateGenre", key = {"ug", "update-genre"})
    public void updateGenre(Long id, String name) {
        genreService.save(Genre.builder().id(id).name(name).build());
    }

    @ShellMethod(value = "PrintGenres", key = {"pg", "print-genres"})
    public void printGenres() {
        genreService.printAll();
    }

    @ShellMethod(value = "DeleteGenre", key = {"dg", "delete-genre"})
    public void deleteGenre(Long id) {
        genreService.deleteById(id);
    }

}
