package ru.otus.library.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import reactor.test.StepVerifier;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@DisplayName("BookRepository:")
@Import(TestRepositoryConfig.class)
class BookRepositoryTest {

    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void beforeEach() {
        reactiveMongoTemplate.dropCollection(Book.class).block();
    }

    @DisplayName("добавляет запись в БД save()")
    @Test
    public void insert() {
        Book book = Book.builder().
                name("TestBook").
                authors(List.of(Author.builder().name("TestAuthor").build())).
                genres(List.of(Genre.builder().name("TestGenre").build())).
                build();

        StepVerifier
                .create(bookRepository.save(book))
                .assertNext(b ->
                    assertThat(b).isEqualToComparingFieldByField(book)
                )
                .expectComplete()
                .verify();
    }

    @DisplayName("получает все записи findAll()")
    @Test
    public void findAll() {
        Book book1 = Book.builder().
                name("TestBook1").
                build();
        Book book2 = Book.builder().
                name("TestBook2").
                build();

        List<Book> books = List.of(book1, book2);
        StepVerifier.create(reactiveMongoTemplate.insertAll(books))
                .expectNextCount(books.size())
                .expectComplete()
                .verify();

        StepVerifier
                .create(bookRepository.findAll())
                .assertNext(b -> assertThat(b).isEqualToComparingFieldByField(book1))
                .assertNext(b -> assertThat(b).isEqualToComparingFieldByField(book2))
                .expectComplete()
                .verify();
    }
}