package ru.otus.library.service;

import de.vandermeer.asciitable.AsciiTable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.Genre;
import ru.otus.library.exceptions.EntityNotFound;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.repository.CommentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;
    private final IOService ioService;
    private final MessageBundleService bundleService;

    @Transactional
    @Override
    public void create(Long bookId, String comment) {
        Book book = bookRepository.getById(bookId).orElseThrow(EntityNotFound::new);
        commentRepository.save(Comment.builder().book(book).commentStr(comment).build());
    }

    @Override
    public void update(Long id, String commentStr) {
        Comment comment = commentRepository.getById(id).orElseThrow(EntityNotFound::new);
        comment.setCommentStr(commentStr);
        commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    @Override
    public void printComments() {
        printTable(commentRepository.getAll());
    }

    @Transactional(readOnly = true)
    @Override
    public void printComments(Long id) {
        printTable(commentRepository.getByBookId(id));
    }

    private void printTable(List<Comment> comments) {
        AsciiTable table = new AsciiTable();
        table.addRule();
        table.addRow(bundleService.getMessage("comment.id"), bundleService.getMessage("comment.book.name"), bundleService.getMessage("comment.commentStr"));

        comments.forEach(comment -> {
            table.addRule();
            table.addRow(comment.getId(), comment.getBook().getName(), comment.getCommentStr());
        });
        table.addRule();
        ioService.print(table.render());
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }
}
