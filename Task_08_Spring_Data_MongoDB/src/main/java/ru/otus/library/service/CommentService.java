package ru.otus.library.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.otus.library.domain.Comment;
import ru.otus.library.dto.CommentDto;

public interface CommentService {
    void create(String bookId, Comment comment);
    void update(String id, Comment comment);
    void printComments(Pageable pageable);
    void printComments(Pageable pageable, String bookId);
    void printComments(Page<CommentDto> page);
    void deleteById(String id);
    void deleteAll();
}
