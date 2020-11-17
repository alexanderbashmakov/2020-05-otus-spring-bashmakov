package ru.otus.library.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.library.domain.Author;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(TestRepositoryConfig.class)
@DisplayName("AuthorRepository:")
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("добавляет запись в БД save()")
    public void insert() {
        Author author = Author.builder().name("TestAuthor").build();
        authorRepository.save(author);
        Author createdAuthor = em.find(Author.class, author.getId());
        assertThat(author).isEqualToComparingFieldByField(createdAuthor);
    }

    @Test
    @DisplayName("обновляет запись в БД save()")
    public void update() {
        Author author = Author.builder().name("TestAuthor").build();
        em.persist(author);
        author.setName("UpdatedName");
        authorRepository.save(author);
        assertThat(em.find(Author.class, author.getId())).isEqualToComparingFieldByField(author);
    }

    @Test
    @DisplayName("удаляет запись из БД deleteById()")
    public void deleteById() {
        Author author = Author.builder().name("AuthorForDelete").build();
        em.persist(author);
        em.detach(author);
        authorRepository.deleteById(author.getId());
        assertThat(em.find(Author.class, author.getId())).isNull();
    }

    @Test
    @DisplayName("получает все записи из БД getAll()")
    void getAll() {
        Author author1 = Author.builder().name("Author1").build();
        em.persist(author1);
        Author author2 = Author.builder().name("Author2").build();
        em.persist(author2);
        assertThat(authorRepository.findAll()).containsExactly(author1, author2);
    }

    @Test
    @DisplayName("получает запись по name из БД getByName()")
    void getByName() {
        Author author = Author.builder().name("newAuthor").build();
        em.persist(author);
        assertThat(authorRepository.getByName("newAuthor").orElse(new Author())).isEqualTo(author);
    }

    @Test
    @DisplayName("получает запись по id из БД getById()")
    void getById() {
        Author author = Author.builder().name("newAuthor").build();
        em.persist(author);
        assertThat(authorRepository.findById(author.getId()).orElse(new Author())).isEqualTo(author);
    }
}