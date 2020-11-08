package ru.otus.library.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.Genre;
import ru.otus.library.repository.BookRepository;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Класс BookServiceImpl:")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = BookServiceImpl.class)
class BookServiceImplTest {

    @Autowired
    private BookService service;

    @MockBean
    private BookRepository repository;

    @MockBean
    private MessageBundleService messageBundleService;

    @DisplayName("сохраняет книгу")
    @Test
    void saveCreate() {
        Book book = Book.builder()
                .id("1L")
                .name("testBook")
                .authors(List.of(Author.builder().name("testAuthor").build()))
                .genres(List.of(Genre.builder().name("testGenre").build()))
                .comments(List.of(Comment.builder().comment("Wow").created(new Date()).build()))
                .build();
        service.save(book);
        verify(repository).save(book);
    }

    @DisplayName("удаляет запись по id deleteById()")
    @Test
    void deleteById() {
        service.deleteById("1L");
        verify(repository, times(1)).deleteById("1L");
    }

    @DisplayName("удаляет все записи deleteAll()")
    @Test
    void deleteAll() {
        service.deleteAll();
        verify(repository).deleteAll();
    }

}