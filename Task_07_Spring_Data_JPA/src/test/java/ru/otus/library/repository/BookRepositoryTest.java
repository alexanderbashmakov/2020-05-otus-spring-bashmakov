package ru.otus.library.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.exceptions.EntityNotFound;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(TestRepositoryConfig.class)
@DisplayName("BookRepository:")
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("получает количество записей count()")
    @Test
    public void returnsBookCount() {
        Book book1 = Book.builder()
                .name("Book1")
                .author(Author.builder()
                        .name("Author1")
                        .build())
                .genre(Genre.builder()
                        .name("Genre1")
                        .build())
                .build();
        em.persist(book1);
        Book book2 = Book.builder()
                .name("Book2")
                .author(Author.builder()
                        .name("Author2")
                        .build())
                .genre(Genre.builder()
                        .name("Genre2")
                        .build())
                .build();
        em.persist(book2);
        Long count = bookRepository.count();
        assertThat(count).isEqualTo(2);
    }

    @DisplayName("возвращает запись по id getById()")
    @Test
    public void getById() {
        Book book = createBook("Book", "Author", "Genre");
        em.persist(book);

        assertThat(bookRepository.findById(book.getId())).get().isEqualToComparingFieldByField(book);
    }

    @Test
    @DisplayName("получает запись по name из БД getByName()")
    void getByName() {
        Book book = createBook("Book", "Author", "Genre");
        em.persist(book);

        assertThat(bookRepository.findByName(book.getName())).get().isEqualToComparingFieldByField(book);
    }


    @DisplayName("добавляет запись в БД insert()")
    @Test
    public void insert() {
        Book expected = Book.builder().
                name("TestBook").
                author(Author.builder().name("TestAuthor1").build()).
                genre(Genre.builder().name("TestGenre1").build()).build();
        bookRepository.save(expected);
        Book actual = em.find(Book.class, expected.getId());
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("удаляет запись deleteById()")
    @Test
    public void deleteById() {
        Book book = createBook("Book", "Author", "Genre");
        em.persist(book);
        em.detach(book);
        bookRepository.deleteById(book.getId());
        val deletedBook = em.find(Book.class, book.getId());
        assertThat(deletedBook).isNull();
    }

    @DisplayName("получает все записи getAll()")
    @Test
    public void getAll() {
        Book book1 = createBook("Book1", "Author1", "Genre1");
        em.persist(book1);
        Book book2 = createBook("Book2", "Author2", "Genre2");
        em.persist(book2);
        em.clear();
        assertThat(bookRepository.findAll()).containsExactly(book1, book2);
    }

    @DisplayName("обновляет запись update()")
    @Test
    public void update() {
        Book book = createBook("Book", "Author", "Genre");
        em.persist(book);
        book.setName("UpdatedBook");
        bookRepository.save(book);
        assertThat(em.find(Book.class, book.getId())).isEqualToComparingFieldByField(book);
    }

    private Book createBook(String bookName, String authorName, String genreName) {
        return Book.builder()
                .name(bookName)
                .author(Author.builder()
                        .name(authorName)
                        .build())
                .genre(Genre.builder()
                        .name(genreName)
                        .build())
                .build();
    }
}