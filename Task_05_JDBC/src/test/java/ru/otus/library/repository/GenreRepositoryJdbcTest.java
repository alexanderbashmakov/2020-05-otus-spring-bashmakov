package ru.otus.library.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Genre;
import ru.otus.library.exceptions.EntityNotFound;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JdbcTest
@Import(GenreRepositoryJdbc.class)
@DisplayName("Класс GenreRepositoryJdbc:")
class GenreRepositoryJdbcTest {

    @Autowired
    private GenreRepository jdbc;

    @Test
    @DisplayName("добавляет запись в БД insert()")
    public void insert() {
        Genre genre = Genre.builder().name("TestGenre").build();
        genre = jdbc.insert(genre);
        Genre createdGenre = jdbc.getById(genre.getId()).orElseGet(Genre::new);
        assertThat(genre).isEqualToComparingFieldByField(createdGenre);
    }

    @Test
    @DisplayName("обновляет запись в БД update()")
    public void update() {
        Genre genre = jdbc.getById(3L).orElseThrow(RuntimeException::new);
        genre.setName("Updated");
        jdbc.update(genre);
        assertThat(jdbc.getById(3L)).get().isEqualToComparingFieldByField(genre);
    }

    @Test
    @DisplayName("удаляет запись из БД deleteById()")
    public void deleteById() {
        jdbc.insert(new Genre(1L, "GenreForDelete"));
        jdbc.deleteById(1L);
        assertThat(jdbc.getById(1L)).isEmpty();
    }

    @Test
    @DisplayName("получает все записи из БД getAll()")
    void getAll() {
        Genre genre1 = jdbc.getById(3L).orElseThrow(EntityNotFound::new);
        Genre genre2 = jdbc.getById(4L).orElseThrow(EntityNotFound::new);
        assertThat(jdbc.getAll()).containsExactly(genre1, genre2);
    }

    @Test
    @DisplayName("получает запись по name из БД getByName()")
    void getByName() {
        Genre genre = new Genre();
        genre.setName("newGenre");
        genre = jdbc.insert(genre);
        assertThat(jdbc.getByName("newGenre").orElse(new Genre())).isEqualTo(genre);
    }

    @Test
    @DisplayName("получает запись по id из БД getById()")
    void getById() {
        Genre genre = new Genre();
        genre.setName("newGenre");
        genre = jdbc.insert(genre);
        assertThat(jdbc.getById(genre.getId()).orElse(new Genre())).isEqualTo(genre);
    }
}