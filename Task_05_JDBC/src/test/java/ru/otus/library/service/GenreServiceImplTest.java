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
import ru.otus.library.domain.Genre;
import ru.otus.library.repository.GenreRepository;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Класс GenreServiceImpl:")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = GenreServiceImpl.class)
class GenreServiceImplTest {

    @Autowired
    private GenreService service;

    @MockBean
    private GenreRepository repository;

    @MockBean
    private IOService ioService;

    @MockBean
    private MessageBundleService messageBundleService;

    @DisplayName("сохраняет запись в БД")
    @Test
    void saveCreate() {
        Genre genre = Genre.builder().name("testGenre").build();
        service.save(genre);
        verify(repository, times(1)).insert(genre);
    }

    @DisplayName("обновляет запись в БД")
    @Test
    void saveUpdate() {
        Genre genre = Genre.builder().id(10L).name("testGenre").build();
        service.save(genre);
        verify(repository, times(1)).update(genre);
    }

    @DisplayName("выводит все записи")
    @Test
    void printAll() {
        Mockito.when(messageBundleService.getMessage("genre.id")).thenReturn("genre_id");
        Mockito.when(messageBundleService.getMessage("genre.name")).thenReturn("genre_name");

        List<Genre> genres = List.of(Genre.builder().id(1L).name("genre").build());
        Mockito.when(repository.getAll()).thenReturn(genres);
        AsciiTable table = new AsciiTable();

        table.addRule();
        table.addRow(messageBundleService.getMessage("genre.id"), messageBundleService.getMessage("genre.name"));
        genres.forEach(genre -> {
            table.addRule();
            table.addRow(genre.getId(), genre.getName());
        });
        table.addRule();

        service.printAll();
        verify(ioService).print(table.render());
    }

    @DisplayName("удаляет запись по id")
    @Test
    void deleteById() {
        service.deleteById(1L);
        verify(repository, times(1)).deleteById(1L);
    }
}