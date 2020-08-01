package ru.otus.library.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Book;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    @EntityGraph(attributePaths = {"author", "genre"})
    Optional<Book> findByName(String name);

    @EntityGraph(attributePaths = {"author", "genre"})
    @Override
    Iterable<Book> findAll();
}
