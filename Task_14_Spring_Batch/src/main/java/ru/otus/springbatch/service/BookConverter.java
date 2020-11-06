package ru.otus.springbatch.service;

import org.springframework.stereotype.Service;
import ru.otus.springbatch.model.AuthorDst;
import ru.otus.springbatch.model.Book;
import ru.otus.springbatch.model.BookDst;
import ru.otus.springbatch.model.GenreDst;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookConverter {

    public BookDst convertBook(Book book) {
        List<AuthorDst> authors = book.getAuthors().stream().map(
                author -> AuthorDst.builder().name(author.getName()).build())
                .collect(Collectors.toList());
        List<GenreDst> genres = book.getGenres().stream().map(
                genre -> GenreDst.builder().name(genre.getName()).build())
                .collect(Collectors.toList());
        return BookDst.builder()
                .name(book.getName())
                .authors(authors)
                .genres(genres)
                .build();
    }
}
