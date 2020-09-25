package ru.otus.library.shell;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.library.domain.Comment;
import ru.otus.library.service.CommentService;

import java.util.Date;
import java.util.UUID;

@ShellComponent
@AllArgsConstructor
public class CommentCommands {
    private final CommentService commentService;

    @ShellMethod(value = "AddComment", key = {"ac", "add-comment"})
    public void addComment(String bookId, String comment) {
        commentService.create(bookId, Comment.builder().comment(comment).created(new Date()).build());
    }

    @ShellMethod(value = "UpdateComment", key = {"uc", "update-comment"})
    public void updateComment(String id, String comment) {
        commentService.update(id, Comment.builder().comment(comment).created(new Date()).build());
    }

    @ShellMethod(value = "PrintComments", key = {"pc", "print-comments"})
    public void printComments(@ShellOption(defaultValue = "0") int page, @ShellOption(defaultValue = "10") int size) {
        commentService.printComments(PageRequest.of(page, size));
    }

    @ShellMethod(value = "PrintCommentsByBookId", key = {"pcb", "print-comments-by-book-id"})
    public void printCommentsByBookId(String bookId, @ShellOption(defaultValue = "0") int page, @ShellOption(defaultValue = "10") int size) {
        commentService.printComments(PageRequest.of(page, size), bookId);
    }

    @ShellMethod(value = "DeleteComment", key = {"dc", "delete-commment"})
    public void deleteComment(String id) {
        commentService.deleteById(id);
    }

}
