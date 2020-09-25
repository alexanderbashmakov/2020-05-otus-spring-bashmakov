package ru.otus.library.repository;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.otus.library.domain.Comment;
import ru.otus.library.dto.CommentDto;

public interface CommentRepository {
    void create(@NonNull String bookId, Comment comment);
    void update(@NonNull String id, Comment comment);
    Page<CommentDto> findComments(Pageable pageable);
    Page<CommentDto> findCommentsByBookId(Pageable pageable, String bookId);
    void deleteById(String id);
    void deleteAll();
}
