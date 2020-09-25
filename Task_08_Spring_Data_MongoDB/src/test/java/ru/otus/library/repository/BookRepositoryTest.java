package ru.otus.library.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.Genre;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@DisplayName("BookRepository:")
@Import(TestRepositoryConfig.class)
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @DisplayName("добавляет запись в БД save()")
    @Test
    public void insert() {
        Book book = Book.builder().
                name("TestBook").
                authors(List.of(Author.builder().name("TestAuthor").build())).
                genres(List.of(Genre.builder().name("TestGenre").build())).
                comments(List.of(Comment.builder().created(new Date()).comment("WoW").build())).
                build();

        bookRepository.save(book);
        Book actual = bookRepository.findById(book.getId()).orElseGet(Book::new);
        assertThat(actual).isEqualToComparingFieldByField(book);
    }
}