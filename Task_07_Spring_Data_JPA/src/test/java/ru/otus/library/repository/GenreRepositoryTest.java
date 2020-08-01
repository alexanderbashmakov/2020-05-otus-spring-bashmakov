package ru.otus.library.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.library.domain.Genre;
import ru.otus.library.exceptions.EntityNotFound;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(TestRepositoryConfig.class)
@DisplayName("GenreRepository:")
class GenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("добавляет запись в БД insert()")
    public void insert() {
        Genre genre = Genre.builder().name("TestGenre").build();
        genreRepository.save(genre);
        Genre createdGenre = em.find(Genre.class, genre.getId());
        assertThat(genre).isEqualToComparingFieldByField(createdGenre);
    }

    @Test
    @DisplayName("обновляет запись в БД update()")
    public void update() {
        Genre genre = Genre.builder().name("TestGenre").build();
        em.persist(genre);
        genre.setName("Updated");
        genreRepository.save(genre);
        assertThat(em.find(Genre.class, genre.getId())).isEqualToComparingFieldByField(genre);
    }

    @Test
    @DisplayName("удаляет запись из БД deleteById()")
    public void deleteById() {
        Genre genre = Genre.builder().name("GenreForDelete").build();
        em.persist(genre);
        em.detach(genre);

        genreRepository.deleteById(genre.getId());
        val deletedGenre = em.find(Genre.class, genre.getId());

        assertThat(deletedGenre).isNull();
    }

    @Test
    @DisplayName("получает все записи из БД getAll()")
    void getAll() {
        Genre genre1 = Genre.builder().name("Genre1").build();
        em.persist(genre1);
        Genre genre2 = Genre.builder().name("Genre2").build();
        em.persist(genre2);
        assertThat(genreRepository.findAll()).containsExactly(genre1, genre2);
    }

    @Test
    @DisplayName("получает запись по name из БД getByName()")
    void getByName() {
        Genre genre = Genre.builder().name("TestGenre").build();
        em.persist(genre);
        assertThat(genreRepository.getByName("TestGenre").orElse(new Genre())).isEqualTo(genre);
    }

    @Test
    @DisplayName("получает запись по id из БД getById()")
    void getById() {
        Genre genre = Genre.builder().name("newGenre").build();
        em.persist(genre);
        assertThat(genreRepository.findById(genre.getId())).get().isEqualToComparingFieldByField(genre);
    }
}