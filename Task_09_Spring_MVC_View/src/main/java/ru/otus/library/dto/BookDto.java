package ru.otus.library.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BookDto {
    private String id;
    private String name;
    private List<String> authors;
    private List<String> genres;

    public static BookDto toDto(Book book) {
        return new BookDto(
                book.getId(),
                book.getName(),
                book.getAuthors().stream().map(Author::getName).collect(Collectors.toList()),
                book.getGenres().stream().map(Genre::getName).collect(Collectors.toList()));
    }

    public static Book toDomainObject(BookDto bookDto) {
        return Book.builder()
                .name(bookDto.getName())
                .authors(
                        bookDto.getAuthors().stream().map(author ->
                                Author.builder().id(UUID.randomUUID().toString()).name(author).build()).collect(Collectors.toList()))
                .genres(
                        bookDto.getGenres().stream().map(genre ->
                                Genre.builder().id(UUID.randomUUID().toString()).name(genre).build()).collect(Collectors.toList()))
                .build();
    }
}
