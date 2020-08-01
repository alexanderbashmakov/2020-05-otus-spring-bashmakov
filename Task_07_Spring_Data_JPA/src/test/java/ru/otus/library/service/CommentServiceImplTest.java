package ru.otus.library.service;

import de.vandermeer.asciitable.AsciiTable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.Genre;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.repository.CommentRepository;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@DisplayName("Класс CommentServiceImpl:")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CommentServiceImpl.class)
class CommentServiceImplTest {

    @Autowired
    private CommentService service;

    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private IOService ioService;

    @MockBean
    private MessageBundleService messageBundleService;

    @DisplayName("сохраняет комментарий")
    @Test
    void create() {
        Book book = Book.builder()
                .id(1L)
                .name("TestBook")
                .build();
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        String commentStr = "testComment";
        service.create(book.getId(), commentStr);
        verify(commentRepository, times(1)).save(argThat(arg -> arg.getBook().equals(book) && arg.getCommentStr().equals(commentStr)));
    }

    @DisplayName("обновляет комментарий")
    @Test
    void saveUpdate() {
        Comment comment = Comment.builder()
                .book(Book.builder()
                        .id(1L)
                        .name("TestBook")
                        .build()
                )
                .id(1L)
                .commentStr("testComment")
                .build();
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));
        String commentStr = "updatedComment";
        service.update(1L, commentStr);
        verify(commentRepository, times(1)).save(argThat(arg -> arg.getId() == 1L && arg.getCommentStr().equals(commentStr)));
    }

    @DisplayName("выводит все записи")
    @Test
    void printAll() {
        Mockito.when(messageBundleService.getMessage("comment.id")).thenReturn("comment_id");
        Mockito.when(messageBundleService.getMessage("comment.book.name")).thenReturn("comment_book_name");
        Mockito.when(messageBundleService.getMessage("comment.commentStr")).thenReturn("comment_commentStr");

        List<Comment> comments = List.of(
                Comment.builder()
                        .id(1L)
                        .commentStr("testCommentStr1")
                        .book(Book.builder()
                                .id(1L)
                                .name("testBook1")
                                .build()
                        )
                        .build(),
                Comment.builder()
                        .id(2L)
                        .commentStr("testCommentStr2")
                        .book(Book.builder()
                                .id(2L)
                                .name("testBook2")
                                .build()
                        )
                        .build()
        );
        Mockito.when(commentRepository.findAll()).thenReturn(comments);

        AsciiTable table = createTable(comments);
        service.printComments();
        verify(ioService).print(table.render());
    }

    private AsciiTable createTable(List<Comment> comments) {
        AsciiTable table = new AsciiTable();

        table.addRule();
        table.addRow(messageBundleService.getMessage("comment.id"), messageBundleService.getMessage("comment.book.name"), messageBundleService.getMessage("comment.commentStr"));
        comments.forEach(comment -> {
            table.addRule();
            table.addRow(comment.getId(), comment.getBook().getName(), comment.getCommentStr());
        });
        table.addRule();
        return table;
    }

    @DisplayName("удаляет запись по id")
    @Test
    void deleteById() {
        service.deleteById(1L);
        verify(commentRepository, times(1)).deleteById(1L);
    }
}