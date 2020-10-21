package ru.otus.library.route;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.dto.BookDto;
import ru.otus.library.repository.BookRepository;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayName("WebRouter:")
class WebRouterTest {

    @Autowired
    private RouterFunction<ServerResponse> route;

    @MockBean
    private BookRepository repository;

    private WebTestClient webTestClient;

    @BeforeEach
    void beforeEach() {
        webTestClient = WebTestClient
                .bindToRouterFunction(route)
                .build();
    }

    @Test
    @DisplayName("Список книг GET /api/book")
    public void findAll() {

        Book book = initBook("Famous Book", new String[] {"Talent"}, new String[] {"Lyrics"});


        when(repository.findAll())
                .thenReturn(Flux.just(book));


        webTestClient.get()
                .uri("/api/book")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.[0].id").isEqualTo(book.getId())
                .jsonPath("$.[0].name").isEqualTo(book.getName())
                .jsonPath("$.[0].authors[0].name").isEqualTo(book.getAuthors().get(0).getName())
                .jsonPath("$.[0].genres[0].name").isEqualTo(book.getGenres().get(0).getName());

        verify(repository).findAll();
    }

    @DisplayName("Сохранение книги POST /api/book")
    @Test
    public void create() {
        Book book = initBook("Famous Book", new String[] {"Talent"}, new String[] {"Lyrics"});
        when(repository.save(any()))
                .thenReturn(Mono.just(book));

        webTestClient.post()
                .uri("/api/book")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(BookDto.toDto(book))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(book.getId())
                .jsonPath("$.name").isEqualTo(book.getName())
                .jsonPath("$.authors[0]").isEqualTo(book.getAuthors().get(0).getName())
                .jsonPath("$.genres[0]").isEqualTo(book.getGenres().get(0).getName());
    }

    @DisplayName("Сохранение книги PUT /api/book")
    @Test
    public void update() {
        String bookId = "1";

        when(repository.deleteById(bookId))
                .thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/api/book/" + bookId)
                .exchange()
                .expectStatus().isOk();

        verify(repository).deleteById(bookId);
    }

    @DisplayName("Удаление книги DELETE /api/book")
    @Test
    public void delete() {
        Book book = initBook("Famous Book", new String[] {"Talent"}, new String[] {"Lyrics"});
        book.setId("1");
        when(repository.save(any()))
                .thenReturn(Mono.just(book));

    }

    private Book initBook(String title, String[] authors, String[] genres) {
        List<Author> authorList = Arrays.stream(authors)
                .map(author -> Author.builder()
                        .id(UUID.randomUUID().toString())
                        .name(author)
                        .build()).collect(Collectors.toList());
        List<Genre> genreList = Arrays.stream(genres)
                .map(genre -> Genre.builder()
                        .id(UUID.randomUUID().toString())
                        .name(genre)
                        .build()).collect(Collectors.toList());
        return Book.builder()
                .name(title)
                .authors(authorList)
                .genres(genreList)
                .build();
    }

}