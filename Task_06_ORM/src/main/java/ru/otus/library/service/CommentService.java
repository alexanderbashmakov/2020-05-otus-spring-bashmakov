package ru.otus.library.service;

public interface CommentService {
    void create(Long bookId, String comment);
    void update(Long id, String comment);
    void printComments();
    void printComment(Long id);
    void deleteById(Long id);
}
