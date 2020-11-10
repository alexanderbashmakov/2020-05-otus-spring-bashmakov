package ru.otus.springbatch.service;

import org.springframework.stereotype.Service;
import ru.otus.springbatch.model.Genre;
import ru.otus.springbatch.model.GenreTmp;

@Service
public class GenreConverter {

    public GenreTmp convertGenre(Genre genre) {
        return GenreTmp.builder().name(genre.getName()).build();
    }
}
