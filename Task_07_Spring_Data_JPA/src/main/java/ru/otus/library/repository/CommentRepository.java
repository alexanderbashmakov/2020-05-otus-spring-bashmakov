package ru.otus.library.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
    List<Comment> findByBookId(Long id);

    @EntityGraph(attributePaths = {"book"})
    @Override
    Iterable<Comment> findAll();

}
