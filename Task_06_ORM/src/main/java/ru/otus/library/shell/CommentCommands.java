package ru.otus.library.shell;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.library.service.CommentService;

@ShellComponent
@AllArgsConstructor
public class CommentCommands {
    private final CommentService commentService;

    @ShellMethod(value = "AddComment", key = {"ac", "add-comment"})
    public void addComment(Long bookId, String comment) {
        commentService.create(bookId, comment);
    }

    @ShellMethod(value = "UpdateComment", key = {"uc", "update-comment"})
    public void updateComment(Long id, String commentStr) {
        commentService.update(id, commentStr);
    }

    @ShellMethod(value = "PrintComments", key = {"pc", "print-comments"})
    public void printComments() {
        commentService.printComments();
    }

    @ShellMethod(value = "PrintCommentsByBookId", key = {"pcb", "print-comments-by-book-id"})
    public void printCommentsByBookId(Long id) { commentService.printComment(id); }

    @ShellMethod(value = "DeleteComment", key = {"dc", "delete-commment"})
    public void deleteComment(Long id) {
        commentService.deleteById(id);
    }

}
