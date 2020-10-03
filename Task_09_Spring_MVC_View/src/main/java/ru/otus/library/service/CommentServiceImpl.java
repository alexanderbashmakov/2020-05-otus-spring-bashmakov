package ru.otus.library.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Comment;
import ru.otus.library.dto.CommentDto;
import ru.otus.library.repository.CommentRepository;

import java.text.SimpleDateFormat;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final IOService ioService;
    private final MessageBundleService bundleService;

    @Transactional
    @Override
    public void create(@NonNull String bookId, Comment comment) {
        commentRepository.create(bookId, comment);
    }

    @Transactional
    @Override
    public void update(@NonNull String id, Comment comment) {
        commentRepository.update(id, comment);
    }

    @Transactional(readOnly = true)
    @Override
    public void printComments(Pageable pageable) {
        printComments(commentRepository.findComments(pageable));
    }

    @Transactional(readOnly = true)
    @Override
    public void printComments(Pageable pageable, String bookId) {
        printComments(commentRepository.findCommentsByBookId(pageable, bookId));
    }

    public void printComments(Page<CommentDto> page) {
    }

    @Transactional
    @Override
    public void deleteById(String id) {
        commentRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteAll() {
        commentRepository.deleteAll();
    }
}
