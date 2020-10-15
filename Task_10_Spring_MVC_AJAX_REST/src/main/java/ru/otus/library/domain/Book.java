package ru.otus.library.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class Book {
    @Id
    private String id;
    private String name;
    private List<Author> authors;
    private List<Genre> genres;
    private List<Comment> comments;
}
