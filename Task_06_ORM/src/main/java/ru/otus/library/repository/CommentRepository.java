package ru.otus.library.repository;

import ru.otus.library.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    Long count();
    Comment save(Comment comment);
    void deleteById(Long id);
    Optional<Comment> getById(Long id);
    List<Comment> getByBookId(Long id);
    List<Comment> getAll();

}
