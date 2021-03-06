package ru.otus.springbatch.service;

import org.springframework.stereotype.Service;
import ru.otus.springbatch.model.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookConverter {

    @PersistenceContext
    private EntityManager em;

    public BookDst convertBook(Book book) {
        List<GenreDst> genres = book.getGenres().stream().map(
                genre -> {
                    TypedQuery<GenreTmp> query = em.createQuery("select g from GenreTmp g where g.name = :name", GenreTmp.class);
                    query.setParameter("name", genre.getName());
                    try {
                        return query.getSingleResult();
                    } catch (Exception e) {
                        return GenreTmp.builder().name(genre.getName()).build();
                    }
                }).map(genreTmp -> GenreDst.builder().id(genreTmp.getId()).name(genreTmp.getName()).build())
                .collect(Collectors.toList());
        return BookDst.builder()
                .name(book.getName())
                .genres(genres)
                .build();
    }
}
