package ru.otus.library.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.exceptions.EntityNotFound;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Класс BookRepositoryJdbc:")
@JdbcTest
@Import({BookRepositoryJdbc.class, AuthorRepositoryJdbc.class, GenreRepositoryJdbc.class})
class BookRepositoryJdbcTest {

    @Autowired
    private BookRepositoryJdbc jdbc;

    @DisplayName("получает количество записей count()")
    @Test
    public void returnsBookCount() {
        int count = jdbc.count();
        assertThat(count).isEqualTo(1);
    }

    @DisplayName("возвращает запись по id getById()")
    @Test
    public void getById() {
        Book expected = Book.builder().
                id(3L).
                name("TestBook").
                author(Author.builder().id(1L).name("TestAuthor1").build()).
                genre(Genre.builder().id(3L).name("TestGenre1").build()).build();
        Book actual = jdbc.getById(3L).orElseThrow(EntityNotFound::new);
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("проверяет отсутствие записи getById()")
    @Test
    public void getByUnknownId() {
        assertThat(jdbc.getById(100L)).isEmpty();
    }

    @Test
    @DisplayName("получает запись по name из БД getByName()")
    void getByName() {
        Book expected = Book.builder().
                id(3L).
                name("TestBook").
                author(Author.builder().id(1L).name("TestAuthor1").build()).
                genre(Genre.builder().id(3L).name("TestGenre1").build()).build();
        Book actual = jdbc.getByName("TestBook").orElseThrow(EntityNotFound::new);
        assertThat(actual).isEqualTo(expected);
    }


    @DisplayName("добавляет запись в БД insert()")
    @Test
    public void insert() {
        Book expected = Book.builder().
                name("TestBook").
                author(Author.builder().name("TestAuthor1").build()).
                genre(Genre.builder().name("TestGenre1").build()).build();
        jdbc.insert(expected);
        Book actual = jdbc.getById(expected.getId()).orElseThrow(EntityNotFound::new);
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("удаляет запись deleteById()")
    @Test
    public void deleteById() {
        jdbc.deleteById(3L);
        int count = jdbc.count();
        assertThat(count).isEqualTo(0);
    }

    @DisplayName("получает все записи getAll()")
    @Test
    public void getAll() {
        Book book = jdbc.getById(3L).orElseThrow(EntityNotFound::new);
        assertThat(jdbc.getAll()).containsOnly(book);
    }

    @DisplayName("обновляет запись update()")
    @Test
    public void update() {
        Book book = jdbc.getById(3L).orElseThrow(EntityNotFound::new);
        book.setName("Updated");
        book.setAuthor(Author.builder().name("Paul").build());
        book.setGenre(Genre.builder().name("Detective").build());
        jdbc.update(book);
        Book actual = jdbc.getById(book.getId()).orElseThrow(EntityNotFound::new);
        assertThat(actual).matches(b -> b.getName().equals(book.getName()) &&
                b.getAuthor().getName().equals(book.getAuthor().getName()) &&
                book.getGenre().getName().equals(book.getGenre().getName()));
    }
}