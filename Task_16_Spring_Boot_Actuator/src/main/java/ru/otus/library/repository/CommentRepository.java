package ru.otus.library.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Comment;

import java.util.List;

@Repository
@RepositoryRestResource(path = "comments")
public interface CommentRepository extends PagingAndSortingRepository<Comment, Long> {
    @RestResource(path = "book")
    List<Comment> findByBookId(Long bookId);

    @EntityGraph(attributePaths = {"book"})
    @Override
    Iterable<Comment> findAll();

}
