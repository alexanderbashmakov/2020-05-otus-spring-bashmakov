package ru.otus.library.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.library.domain.Genre;
import ru.otus.library.dto.GenreDto;
import ru.otus.library.repository.GenreRepository;

import java.util.List;

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
    private MessageBundleService messageBundleService;

    @DisplayName("сохраняет жанр create()")
    @Test
    void createGenre() {
        String bookId = "1";
        Genre genre = Genre.builder().name("testGenre").build();
        service.create(bookId, genre);
        verify(repository).create(bookId, genre);
    }

    @DisplayName("обновляет жанр update()")
    @Test
    void updateGenre() {
        String id = "1";
        Genre genre = Genre.builder().name("testGenre").build();
        service.update(id, genre);
        verify(repository).update(id, genre);
    }

    @DisplayName("удаляет запись по id deleteById()")
    @Test
    void deleteById() {
        service.deleteById("1L");
        verify(repository).deleteById("1L");
    }

    @DisplayName("удаляет все записи deleteAll()")
    @Test
    void deleteAll() {
        service.deleteAll();
        verify(repository).deleteAll();
    }
}