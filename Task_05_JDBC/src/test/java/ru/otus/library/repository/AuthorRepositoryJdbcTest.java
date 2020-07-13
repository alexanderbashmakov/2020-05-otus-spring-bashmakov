package ru.otus.library.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.library.domain.Author;
import ru.otus.library.exceptions.EntityNotFound;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JdbcTest
@Import(AuthorRepositoryJdbc.class)
@DisplayName("Класс AuthorRepositoryJdbc:")
class AuthorRepositoryJdbcTest {

    @Autowired
    private AuthorRepository jdbc;

    @Test
    @DisplayName("добавляет запись в БД insert()")
    public void insert() {
        Author author = Author.builder().name("TestAuthor").build();
        author = jdbc.insert(author);
        Author createdAuthor = jdbc.getById(author.getId()).orElseGet(Author::new);
        assertThat(author).isEqualToComparingFieldByField(createdAuthor);
    }

    @Test
    @DisplayName("обновляет запись в БД update()")
    public void update() {
        Author author = jdbc.getById(1L).orElseThrow(RuntimeException::new);
        author.setName("Updated");
        jdbc.update(author);
        assertThat(jdbc.getById(1L)).get().isEqualToComparingFieldByField(author);
    }

    @Test
    @DisplayName("выбрасывает исключение update()")
    public void throwOnUpdate() {
        Author author = Author.builder().id(3L).name("Unknown").build();
        assertThatThrownBy(() -> jdbc.update(author)).isInstanceOf(EntityNotFound.class);
    }

    @Test
    @DisplayName("удаляет запись из БД deleteById()")
    public void deleteById() {
        jdbc.insert(new Author(10L, "AuthorForDelete"));
        jdbc.deleteById(10L);
        assertThat(jdbc.getById(10L)).isEmpty();
    }

    @Test
    @DisplayName("получает все записи из БД getAll()")
    void getAll() {
        Author author1 = jdbc.getById(1L).orElseThrow(EntityNotFound::new);
        Author author2 = jdbc.getById(2L).orElseThrow(EntityNotFound::new);
        assertThat(jdbc.getAll()).containsExactly(author1, author2);
    }

    @Test
    @DisplayName("получает запись по name из БД getByName()")
    void getByName() {
        Author author = new Author();
        author.setName("newAuthor");
        author = jdbc.insert(author);
        assertThat(jdbc.getByName("newAuthor").orElse(new Author())).isEqualTo(author);
    }

    @Test
    @DisplayName("получает запись по id из БД getById()")
    void getById() {
        Author author = new Author();
        author.setName("newAuthor");
        author = jdbc.insert(author);
        assertThat(jdbc.getById(author.getId()).orElse(new Author())).isEqualTo(author);
    }
}