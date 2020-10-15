package ru.otus.library.repository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Comment;
import ru.otus.library.dto.CommentDto;

@RequiredArgsConstructor
@Repository
public class CommentRepositoryImpl implements CommentRepository {
    private final BookOperations bookOperations;
    private static final String ARRAY_NAME = "comments";

    @Override
    public void create(@NonNull String bookId, Comment comment) {
        bookOperations.createElement(bookId, comment, ARRAY_NAME);
    }

    @Override
    public void update(@NonNull String id, Comment comment) {
        bookOperations.updateElement(id, comment, ARRAY_NAME);
    }

    @Override
    public Page<CommentDto> findComments(Pageable pageable) {
        return bookOperations.findElements(pageable, null, CommentDto.class, ARRAY_NAME);
    }

    @Override
    public Page<CommentDto> findCommentsByBookId(Pageable pageable, String bookId) {
        return bookOperations.findElements(pageable, bookId, CommentDto.class, ARRAY_NAME);
    }

    @Override
    public void deleteById(String id) {
        bookOperations.deleteById(id, ARRAY_NAME);
    }

    @Override
    public void deleteAll() {
        bookOperations.deleteAll(ARRAY_NAME);
    }
}
