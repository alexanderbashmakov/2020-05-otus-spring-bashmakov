package ru.otus.library.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.library.domain.Author;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AuthorDto {
    private String id;
    private String bookId;
    private String bookName;
    private String name;

    public static AuthorDto toDto(Author author, String bookId) {
        return new AuthorDto(
                author.getId(),
                bookId,
                "",
                author.getName());
    }

    public static Author toDomainObject(AuthorDto authorDto) {
        return Author.builder()
                .id(authorDto.getId())
                .name(authorDto.getName())
                .build();
    }

}
