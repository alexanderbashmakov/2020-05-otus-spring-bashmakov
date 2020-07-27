package ru.otus.library.service;

import ru.otus.library.domain.Comment;

public interface CommentService {
    void create(Long bookId, String comment);
    void update(Long id, String comment);
    void printComments();
    void printComments(Long id);
    void deleteById(Long id);
}
