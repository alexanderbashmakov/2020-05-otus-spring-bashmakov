package ru.otus.library.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.library.domain.Genre;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class GenreDto {
    private String id;
    private String bookId;
    private String bookName;
    private String name;

    public static GenreDto toDto(Genre genre, String bookId) {
        return new GenreDto(
                genre.getId(),
                bookId,
                "",
                genre.getName());
    }

    public static Genre toDomainObject(GenreDto genreDto) {
        return Genre.builder()
                .id(genreDto.getId())
                .name(genreDto.getName())
                .build();
    }

}
