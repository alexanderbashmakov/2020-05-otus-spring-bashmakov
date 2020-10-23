package ru.otus.library.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.otus.library.domain.Book;
import ru.otus.library.dto.BookDto;
import ru.otus.library.repository.BookRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@RequiredArgsConstructor
@Component
public class BookHandler {

    private final BookRepository repository;

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ok().contentType(APPLICATION_JSON).body(repository.findAll(), Book.class);
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(BookDto.class)
                .map(BookDto::toDomainObject)
                .flatMap(repository::save)
                .map(BookDto::toDto)
                .flatMap(bookDto -> ok()
                        .contentType(APPLICATION_JSON)
                        .body(Mono.just(bookDto), BookDto.class)
                );
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        return request.bodyToMono(BookDto.class)
                .map(BookDto::toDomainObject)
                .flatMap(book -> {
                    book.setId(request.pathVariable("id"));
                    return repository.save(book);
                })
                .map(BookDto::toDto)
                .flatMap(bookDto -> ok()
                        .contentType(APPLICATION_JSON)
                        .body(Mono.just(bookDto), BookDto.class)
                );
    }

    public Mono<ServerResponse> deleteById(ServerRequest request) {
        return repository.deleteById(request.pathVariable("id")).then(ok().contentType(APPLICATION_JSON).build());
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        return ok().contentType(APPLICATION_JSON).body(repository.findById(request.pathVariable("id")), Book.class);
    }
}
